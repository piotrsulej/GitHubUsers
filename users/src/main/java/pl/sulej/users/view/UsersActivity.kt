package pl.sulej.users.view

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_users.*
import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.view.user.User
import pl.sulej.utilities.adapter.BaseAdapter
import pl.sulej.utilities.view.TextChangedListener
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
        setContentView(R.layout.activity_users)
        initializeUsersList()
        initializeSearchBar()
        initializeErrorCard()
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
        users_list.visibility = View.VISIBLE
        users_search.visibility = View.VISIBLE
        loading_indicator.visibility = View.GONE
        error_message.visibility = View.GONE
        error_details.visibility = View.GONE
    }

    override fun showLoadingIndicator() {
        loading_indicator.visibility = View.VISIBLE
        users_list.visibility = View.GONE
        users_search.visibility = View.GONE
        error_message.visibility = View.GONE
        error_details.visibility = View.GONE
    }

    override fun showError(errorMessage: String) {
        error_message.visibility = View.VISIBLE
        error_details.visibility = View.VISIBLE
        error_details.setOnClickListener {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
        loading_indicator.visibility = View.GONE
        users_list.visibility = View.GONE
        users_search.visibility = View.GONE
    }

    private fun initializeUsersList() {
        users_list.layoutManager = LinearLayoutManager(this)
        adapter = adapterFactory.create()
        users_list.adapter = adapter
    }

    private fun initializeErrorCard() {
        error_message.setOnClickListener {
            presenter.errorClicked()
        }
    }

    private fun initializeSearchBar() {
        users_search.addTextChangedListener(object : TextChangedListener() {
            override fun afterTextChanged(editable: Editable?) {
                presenter.searchQueryUpdated(editable.toString())
            }
        })
    }
}