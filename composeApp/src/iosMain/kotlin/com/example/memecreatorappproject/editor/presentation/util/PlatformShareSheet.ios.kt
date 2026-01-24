package com.example.memecreatorappproject.editor.presentation.util

import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual class PlatformShareSheet {
    actual fun shareFile(filePath: String) {
        val fileUrl = NSURL.fileURLWithPath(filePath)
        val itemsToShare = listOf(fileUrl)

        //Controlled used to trigger the share sheet
        val activityViewController = UIActivityViewController(
            activityItems = itemsToShare,
            applicationActivities = null
        )

        //used to overlay certain items on that root view
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            ?: throw IllegalStateException("No root view controller found.")

        rootViewController.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null
        )
    }
}