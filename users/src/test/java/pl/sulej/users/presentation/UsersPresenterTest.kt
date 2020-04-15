package pl.sulej.users.presentation

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Single
import org.junit.Test
import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.data.UserDTO
import pl.sulej.users.model.data.UserDetails
import pl.sulej.users.view.data.User
import pl.sulej.utilities.asynchronicity.TEST_SUBSCRIPTIONS_MANAGER
import pl.sulej.utilities.design.Converter

class UsersPresenterTest {

    private val model: UsersModel = mock()
    private val converter: Converter<List<UserDetails>, List<User>> = mock()
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
            .willReturn(Single.just(DUMMY_USERS_FROM_MODEL))
        given(converter.convert(DUMMY_USERS_FROM_MODEL))
            .willReturn(DUMMY_USERS)

        testSubject.viewAvailable()

        then(view).should()
            .showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show users with updated repositories`() {
        given(model.getUsers())
            .willReturn(Single.just(emptyList()))
        given(converter.convert(emptyList()))
            .willReturn(emptyList())
        testSubject.viewAvailable()

        given(model.getUsersWithRepositoriesOfUser(DUMMY_LOGIN))
            .willReturn(Single.just(DUMMY_USERS_FROM_MODEL))
        given(converter.convert(DUMMY_USERS_FROM_MODEL))
            .willReturn(DUMMY_USERS)

        testSubject.userClicked(DUMMY_LOGIN)

        then(view).should()
            .showUsers(DUMMY_USERS)
    }

    companion object {
        private const val DUMMY_LOGIN = "Gorn"
        private val DUMMY_USERS_FROM_MODEL = listOf(
            UserDetails(
                userDTO = UserDTO(
                    login = "Gorn",
                    avatarUrl = "https://custom-gwent.com/cardsBg/8b404ca7f758f2af1eb16e4569c2ca68.jpeg"
                ),
                repositories = emptyList()
            )
        )
        private val DUMMY_USERS = listOf(
            User(
                name = "Gorn",
                avatarUrl = "https://custom-gwent.com/cardsBg/8b404ca7f758f2af1eb16e4569c2ca68.jpeg",
                repositoryNames = "Zemsta Gorna, Zbroja najemnika Nowy Obóz"
            )
        )
    }
}