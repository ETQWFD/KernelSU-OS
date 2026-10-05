# KernelSU OS — Modifications & Legal Notice

KernelSU OS is a **modified redistribution** of the upstream project
[KernelSU](https://github.com/tiann/KernelSU) by tiann and its contributors.

KernelSU is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.
This fork therefore remains under GPL-3.0. It is **not** MIT-licensed, and it
cannot be relicensed under MIT. All original copyright notices in the source
files are retained.

- Upstream: https://github.com/tiann/KernelSU
- Upstream license: GPL-3.0 (see `LICENSE`)
- Modifier of this distribution: **etc**
- Application name: **KernelSU OS**
- Application ID: `etc.kernelsu.os`

## Changes relative to upstream

1. **32-bit ARM (armv7 / armeabi-v7a) support for the manager**
   - Added `armeabi-v7a` to the manager's `abiFilters`.
   - Cross-compiled the `ksud` daemon for `armv7-linux-androideabi` and shipped
     it as `lib/armeabi-v7a/libksud.so`, alongside the arm64 build.
   - Added 32-bit armv7 runtime assets (busybox) under
     `userspace/ksud/bin/arm/`.
   - Lowered `minSdkVersion` from 31 to 29 so the manager installs and runs on
     Android 10 devices.
   - Fixed a 32-bit portability bug in `userspace/ksud/src/module.rs`:
     `timespec.tv_nsec` is `i64` on 64-bit ABIs but `i32` on armv7; the value is
     now cast with `as _` instead of `.into()` from `u32`.

2. **Rebranding**
   - Display name changed to "KernelSU OS" and application ID to
     `etc.kernelsu.os`.
   - "About" page attributes the original KernelSU authors and states GPL-3.0.

3. **In-app self-update against this fork's releases**
   - The update check now queries the latest GitHub Release of this repository
     and downloads the new APK inside the app, then launches the system package
     installer.

4. **Bundled runtime components**
   - `busybox`, `ksuinit` (arm64) and `ksud` are embedded per ABI.

## Important limitation

Installing this manager does **not** grant root. KernelSU requires its code to
be integrated into the device kernel (boot image) and flashed with an unlocked
bootloader. On most 32-bit devices this additionally needs the device-specific
32-bit kernel source, which is not provided here. The app only makes the
manager UI installable and usable on 32-bit Android 10 phones.

Warranty: this program is distributed WITHOUT ANY WARRANTY, as permitted by
the GPL-3.0. Flashing kernels may brick your device; you bear that risk.

## v1.1.0-OS additional changes

5. **Bundled official GKI kernel modules**
   - The eight official aarch64 GKI `*_kernelsu.ko` images from upstream
     release v3.3.0 (android12-5.10 through android17-6.18) are shipped:
       - copied into `manager/app/src/main/assets/gki/` (inside the APK);
       - force-added under `userspace/ksud/bin/aarch64/` so the rebuilt
         aarch64 `ksud` embeds them via RustEmbed, letting GKI devices patch
         a boot/kernel image without a separate download.
   - On first launch the manager requests storage permission and exports
     these images to the public `Download/KernelSU OS/` folder (MediaStore
     Downloads on API 29+; direct write below that), then shows a dialog with
     the full path. A Home entry can re-export them at any time.
   - GKI images only apply to GKI kernels (5.10+). Non-GKI 4.14/4.19 devices
     cannot gain root from them.

6. **Rebranded external links**
   - The Home "Learn" entry and the former donation entry now open the
     KernelSU OS website / this repository instead of kernelsu.org or
     patreon.com.

7. **Version bumped** to v1.1.0-OS (versionCode 2). Same signing key as
   v1.0.0-OS, so upgrades install over the previous build.
