package com.lopukh.crossnote

import com.google.firebase.auth.FirebaseUser

class UserModel(currentUser: FirebaseUser) {
    var userId = currentUser.uid
    var email = currentUser.email!!
}