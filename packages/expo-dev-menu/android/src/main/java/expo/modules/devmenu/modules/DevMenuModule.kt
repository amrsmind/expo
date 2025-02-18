package expo.modules.devmenu.modules

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import expo.modules.devmenu.DevMenuManager
import kotlinx.coroutines.launch


class DevMenuModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  override fun getName() = "ExpoDevMenu"

  private val devMenuManager: DevMenuManager = DevMenuManager

  private fun openMenuOn(screen: String?) {
    reactApplicationContext
      .currentActivity
      ?.run {
        devMenuManager.openMenu(this, screen)
      }
  }

  @ReactMethod
  fun isLoggedInAsync(promise: Promise) {
    promise.resolve(
      devMenuManager
        .getExpoApiClient()
        .isLoggedIn()
    )
  }

  @ReactMethod
  fun queryMyProjectsAsync(promise: Promise) {
    devMenuManager.coroutineScope.launch {
      try {
        devMenuManager
          .getExpoApiClient()
          .queryMyProjects()
          .use {
            @Suppress("DEPRECATION_ERROR")
            promise.resolve(it.body()?.charStream()?.readText() ?: "")
          }
      } catch (e: Exception) {
        promise.reject("ERR_DEVMENU_CANNOT_GET_PROJECTS", e.message, e)
      }
    }
  }

  @ReactMethod
  fun queryDevSessionsAsync(deviceID: String?, promise: Promise) {
    devMenuManager.coroutineScope.launch {
      try {
        devMenuManager
          .getExpoApiClient()
          .queryDevSessions(deviceID)
          .use {
            @Suppress("DEPRECATION_ERROR")
            promise.resolve(it.body()?.charStream()?.readText() ?: "")
          }
      } catch (e: Exception) {
        promise.reject("ERR_DEVMENU_CANNOT_GET_DEV_SESSIONS", e.message, e)
      }
    }
  }

  @ReactMethod
  fun openMenu() {
    openMenuOn(null)
  }

  @ReactMethod
  fun openProfile() {
    openMenuOn("Profile")
  }

  @ReactMethod
  fun openSettings() {
    openMenuOn("Settings")
  }
}
