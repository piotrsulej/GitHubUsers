package pl.sulej.users.presentation

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Test
import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.model.UsersModel
import pl.sulej.users.model.UserDetails
import pl.sulej.users.view.user.User
import pl.sulej.utilities.TWICE
import pl.sulej.utilities.asynchronicity.TestSubscriptionsMapManager
import pl.sulej.utilities.asynchronicity.asObservable
import pl.sulej.utilities.design.Converter
import pl.sulej.utilities.resources.StringProvider

class UsersPresenterTest {

    private val model: UsersModel = mock()
    private val converter: Converter<FilteredUserList, List<User>> = mock {
        on { convert(CONVERTER_EXPECTED_LIST) } doReturn DUMMY_USERS
    }
    private val view: UsersContract.View = mock()
    private val stringProvider: StringProvider = mock {
        on { getString(R.string.unknown_error) } doReturn DEFAULT_ERROR_MESSAGE
    }
    private val subscriptionsManager = spy(TestSubscriptionsMapManager.INSTANCE)
    private val testSubject = UsersPresenter(model, converter, subscriptionsManager, stringProvider)
        .apply { viewCreated(view) }

    @Test
    fun `Unsubscribe presenter`() {
        testSubject.viewUnavailable()

        then(subscriptionsManager).should().unsubscribe(testSubject.toString())
    }

    @Test
    fun `Show loading indicator`() {
        given(model.getUsers()).willReturn(Observable.empty())

        testSubject.viewAvailable()

        then(view).should().showLoadingIndicator()
    }

    @Test
    fun `Show users`() {
        given(model.getUsers()).willReturn(DUMMY_MODEL_USERS.asObservable())

        testSubject.viewAvailable()

        then(view).should().showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show updated users`() {
        given(model.getUsers()).willReturn(Observable.just(DUMMY_MODEL_USERS, DUMMY_MODEL_USERS))
        val list = FilteredUserList(DUMMY_MODEL_USERS)
        given(converter.convert(list)).willReturn(DUMMY_USERS)

        testSubject.viewAvailable()

        then(view).should(TWICE).showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show error`() {
        given(model.getUsers()).willReturn(Observable.error(DUMMY_ERROR))

        testSubject.viewAvailable()

        then(view).should().showError(DUMMY_ERROR_MESSAGE)
    }

    @Test
    fun `Show default error`() {
        given(model.getUsers()).willReturn(Observable.error(ERROR_WITHOUT_MESSAGE))

        testSubject.viewAvailable()

        then(view).should().showError(DEFAULT_ERROR_MESSAGE)
    }

    @Test
    fun `Show users after clicking error`() {
        given(model.getUsers()).willReturn(DUMMY_MODEL_USERS.asObservable())

        testSubject.errorClicked()

        then(view).should().showUsers(DUMMY_USERS)
    }

    @Test
    fun `Show filtered users`() {
        given(model.getUsers()).willReturn(DUMMY_MODEL_USERS.asObservable())

        val filteredList = FilteredUserList(DUMMY_MODEL_USERS, searchQuery = DUMMY_LOGIN)
        given(converter.convert(filteredList)).willReturn(DUMMY_USERS)

        testSubject.searchQueryUpdated(DUMMY_LOGIN)

        then(view).should().showUsers(DUMMY_USERS)
    }

    companion object {
        private const val DUMMY_LOGIN = "Gorn"
        private const val DUMMY_ERROR_MESSAGE =
            "Gorn dużo narozrabiał. Przynieś mi pieniądze, a ja puszczę go wolno."
        private const val DEFAULT_ERROR_MESSAGE =
            "To niemożliwe. Popełnił wiele przestępstw i musi za nie odpokutować."
        private val DUMMY_ERROR = Throwable(DUMMY_ERROR_MESSAGE)
        private val NO_ERROR_MESSAGE: String? = null
        private val ERROR_WITHOUT_MESSAGE = Throwable(NO_ERROR_MESSAGE)
        private val DUMMY_MODEL_USERS = listOf(
            UserDetails(
                login = "Gorn",
                avatarUrl = "https://custom-gwent.com/cardsBg/8b404ca7f758f2af1eb16e4569c2ca68.jpeg",
                repositoryNames = listOf(
                    "Zemsta Gorna",
                    "Zbroja najemnika",
                    "Nowy Obóz"
                )
            )
        )
        private val CONVERTER_EXPECTED_LIST = FilteredUserList(DUMMY_MODEL_USERS)
        private val DUMMY_USERS = listOf(
            User(
                name = "Gorn",
                avatarUrl = "https://custom-gwent.com/cardsBg/8b404ca7f758f2af1eb16e4569c2ca68.jpeg",
                repositoriesInfo = "Zemsta Gorna, Zbroja najemnika, Nowy Obóz",
                isLoadingRepositories = false
            )
        )
    }
}