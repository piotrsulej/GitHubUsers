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
import pl.sulej.users.view.UserDetailClick
import pl.sulej.users.view.data.User
import pl.sulej.utilities.asynchronicity.TEST_SUBSCRIPTIONS_MANAGER
import pl.sulej.utilities.design.Converter

class UsersPresenterTest {

    private val model: UsersModel = mock()
    private val converter: Converter<UserList, List<User>> = mock()
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
        given(model.getUsers()).willReturn(Single.just(DUMMY_MODEL_USERS))
        val list = UserList(DUMMY_MODEL_USERS)
        given(converter.convert(list)).willReturn(DUMMY_USERS)

        testSubject.viewAvailable()

        then(view).should().showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show filtered users`() {
        given(model.getUsers()).willReturn(Single.just(DUMMY_MODEL_USERS))
        val filteredList = UserList(DUMMY_MODEL_USERS, searchQuery = DUMMY_LOGIN)
        given(converter.convert(filteredList)).willReturn(DUMMY_USERS)

        testSubject.searchQueryUpdated(DUMMY_LOGIN)

        then(view).should().showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show expanded user`() {
        given(model.getUsersWithRepositoriesOfUser(DUMMY_LOGIN))
            .willReturn(Single.just(DUMMY_MODEL_USERS))
        val list = UserList(DUMMY_MODEL_USERS, listOf(DUMMY_LOGIN))
        given(converter.convert(list)).willReturn(DUMMY_USERS)

        testSubject.userDetailsClicked(UserDetailClick(userName = DUMMY_LOGIN, expanded = true))

        then(view).should().showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show collapsed user`() {
        given(model.getUsersWithRepositoriesOfUser(DUMMY_LOGIN))
            .willReturn(Single.just(DUMMY_MODEL_USERS))
        val list = UserList(DUMMY_MODEL_USERS)
        given(converter.convert(list)).willReturn(DUMMY_USERS)

        testSubject.userDetailsClicked(UserDetailClick(userName = DUMMY_LOGIN, expanded = false))

        then(view).should().showUsers(DUMMY_USERS)
    }

    companion object {
        private const val DUMMY_LOGIN = "Gorn"
        private val DUMMY_MODEL_USERS = listOf(
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
                repositoryNames = "Zemsta Gorna, Zbroja najemnika Nowy Obóz",
                detailsExpanded = false
            )
        )
    }
}