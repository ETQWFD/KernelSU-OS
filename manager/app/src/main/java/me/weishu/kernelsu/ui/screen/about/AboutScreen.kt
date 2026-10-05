package me.weishu.kernelsu.ui.screen.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.dropUnlessResumed
import me.weishu.kernelsu.BuildConfig
import me.weishu.kernelsu.R
import me.weishu.kernelsu.ui.LocalUiMode
import me.weishu.kernelsu.ui.UiMode
import me.weishu.kernelsu.ui.navigation3.LocalNavigator

@Composable
fun AboutScreen() {
    val navigator = LocalNavigator.current
    val uriHandler = LocalUriHandler.current
    val baseHtml = stringResource(
        id = R.string.about_source_code,
        "<b><a href=\"https://github.com/tiann/KernelSU\">GitHub</a></b>",
        "<b><a href=\"https://t.me/KernelSU\">Telegram</a></b>"
    )
    // KernelSU OS: GPL-3.0 requires preserving upstream attribution.
    val htmlString = baseHtml +
        "<br/><br/><b>KernelSU OS</b> is a modified distribution by <b>etc</b>, " +
        "based on <b><a href=\"https://github.com/tiann/KernelSU\">KernelSU</a></b> " +
        "by tiann &amp; contributors, licensed under " +
        "<b><a href=\"https://www.gnu.org/licenses/gpl-3.0.html\">GPL-3.0</a></b>. " +
        "Source: <b><a href=\"https://github.com/ETQWFD/KernelSU-OS\">ETQWFD/KernelSU-OS</a></b>."
    val state = AboutUiState(
        title = stringResource(R.string.about),
        appName = stringResource(R.string.app_name),
        versionName = BuildConfig.VERSION_NAME,
        links = extractLinks(htmlString),
    )
    val actions = AboutScreenActions(
        onBack = dropUnlessResumed { navigator.pop() },
        onOpenLink = uriHandler::openUri,
    )

    when (LocalUiMode.current) {
        UiMode.Miuix -> AboutScreenMiuix(state, actions)
        UiMode.Material -> AboutScreenMaterial(state, actions)
    }
}
