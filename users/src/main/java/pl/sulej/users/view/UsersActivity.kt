package pl.sulej.users.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.view.adapter.UsersAdapterFactory
import pl.sulej.users.view.data.User
import pl.sulej.utilities.adapter.BaseAdapter
import javax.inject.Inject

class UsersActivity : AppCompatActivity(), UsersContract.View {

    @Inject
    lateinit var adapterFactory: UsersAdapterFactory
    @Inject
    lateinit var presenter: UsersContract.Presenter

    private lateinit var adapter: BaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        presenter.viewCreated(this)
        setContentView(R.layout.activity_main)
        initializeUsersList()
        initializeSearchBar()
    }

    override fun onResume() {
        super.onResume()
        presenter.viewAvailable()
    }

    override fun onPause() {
        super.onPause()
        presenter.viewUnavailable()
    }

    override fun showUsers(users: List<User>) {
        adapter.items = users
    }

    private fun initializeUsersList() {
        users_list.layoutManager = LinearLayoutManager(this)
        adapter = adapterFactory.create(
            userDetailsClicked = presenter::userDetailsClicked
        )
        users_list.adapter = adapter
    }

    private fun initializeSearchBar() {
        users_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchQueryUpdated(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.searchQueryUpdated(query)
                return true
            }
        })
    }
}