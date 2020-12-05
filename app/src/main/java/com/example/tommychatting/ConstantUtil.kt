package com.example.tommychatting
import androidx.multidex.BuildConfig


class ConstantUtil {
    companion object {
        const val API_KEY = "AAAAQ7gp5rM:APA91bFr7dDtwIeZpBIqb3RDRPdxOjVoEn5bdKq3auhSD8j2EI7N5coyaMS3hPulHvG1bTtgvutDuqr8kgKwE02uOpwl_K3NxfUZplaH6geVwYQ-eTVEHfGomQOMPOe3tGwRYRV03ffs"
        const val COLLECTION = "users"
        const val NOTIFICATION_ID = 123
        const val NOTIFICATION_CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.fcm"
        const val NOTIFICATION_CHANNEL_NAME = "Android Material Push Notification"
    }
}