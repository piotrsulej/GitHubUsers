package pl.sulej.users.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.view.adapter.UsersAdapter
import pl.sulej.users.view.data.User

class UsersActivity : AppCompatActivity(), UsersContract.View {

    lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        users_list.adapter = adapter
    }

    override fun showUsers(users: List<User>) {
        adapter.items = users
    }
}