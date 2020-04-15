package pl.sulej.users.presentation

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Single
import org.junit.Test
import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.view.data.User
import pl.sulej.utilities.Converter
import pl.sulej.utilities.TEST_SUBSCRIPTIONS_MANAGER

class UsersPresenterTest {

    private val model: UsersModel = mock()
    private val converter: Converter<List<UserDTO>, List<User>> = mock()
    private val view: UsersContract.View = mock()
    private val testSubject = UsersPresenter(
        model = model,
        converter = converter,
        subscriptionsManager = TEST_SUBSCRIPTIONS_MANAGER
    ).apply {
        viewCreated(view)
    }

    @Test
    fun `Show users`() {
        given(model.getUsers())
            .willReturn(Single.just(DUMMY_USER_DTOs))
        given(converter.convert(DUMMY_USER_DTOs))
            .willReturn(DUMMY_USERS)

        testSubject.viewAvailable()

        then(view).should()
            .showUsers(DUMMY_USERS)
    }

    companion object {
        private val DUMMY_USER_DTOs = listOf(
            UserDTO(
                login = "Gorn",
                avatar_url = "https://custom-gwent.com/cardsBg/8b404ca7f758f2af1eb16e4569c2ca68.jpeg",
                url = "http://gorn.eu"
            )
        )
        private val DUMMY_USERS = listOf(
            User(
                name = "Gorn",
                avatarUrl = "https://custom-gwent.com/cardsBg/8b404ca7f758f2af1eb16e4569c2ca68.jpeg",
                repositoryNames = listOf("Zemsta Gorna", "Zbroja najemnika", "Nowy Obóz")
            )
        )
    }
}