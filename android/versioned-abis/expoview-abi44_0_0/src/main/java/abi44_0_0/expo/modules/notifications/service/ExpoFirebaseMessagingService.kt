package abi44_0_0.expo.modules.notifications.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import abi44_0_0.expo.modules.notifications.service.interfaces.FirebaseMessagingDelegate

open class ExpoFirebaseMessagingService : FirebaseMessagingService() {
  protected open val firebaseMessagingDelegate: FirebaseMessagingDelegate by lazy {
    abi44_0_0.expo.modules.notifications.service.delegates.FirebaseMessagingDelegate(this)
  }

  override fun onMessageReceived(remoteMessage: RemoteMessage) = firebaseMessagingDelegate.onMessageReceived(remoteMessage)
  override fun onNewToken(token: String) = firebaseMessagingDelegate.onNewToken(token)
  override fun onDeletedMessages() = firebaseMessagingDelegate.onDeletedMessages()
}
