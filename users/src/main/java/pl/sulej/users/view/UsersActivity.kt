package pl.sulej.users.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.sulej.users.R
import pl.sulej.users.UsersContract
import pl.sulej.users.view.data.User

class UsersActivity : AppCompatActivity(), UsersContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showUsers(users: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}