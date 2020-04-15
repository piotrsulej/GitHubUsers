package pl.sulej.users.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.view.adapter.UsersAdapter
import pl.sulej.users.view.data.User
import javax.inject.Inject

class UsersActivity : AppCompatActivity(), UsersContract.View {

    @Inject
    lateinit var adapter: UsersAdapter
    @Inject
    lateinit var presenter: UsersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        presenter.viewCreated(this)
        setContentView(R.layout.activity_main)
        users_list.layoutManager = LinearLayoutManager(this)
        users_list.adapter = adapter
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
}