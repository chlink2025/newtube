<div align="center">

<img src=".github/assets/icon.png" width="112" height="112" alt="NewTube icon">

# NewTube

### 基于 SmartTube，为手机而生。

**非官方的手机版 SmartTube**：Android 手机和平板用的 YouTube 客户端，基于
[@yuliskov](https://github.com/yuliskov) 的 [SmartTube](https://github.com/yuliskov/SmartTube)。<br>
用代码登录，后台继续播，下载视频离线看，还能投屏到电视上的 SmartTube。

[![Latest release](https://img.shields.io/github/v/release/aleixrodriala/newtube?style=flat-square&label=release&color=1E2A78)](https://github.com/aleixrodriala/newtube/releases/latest)
[![Android 7.0+](https://img.shields.io/badge/Android-7.0%2B-1E2A78?style=flat-square)](#下载)
[![License: MIT](https://img.shields.io/badge/license-MIT-1E2A78?style=flat-square)](LICENSE)
[![Built on SmartTube](https://img.shields.io/badge/built_on-SmartTube-1E2A78?style=flat-square)](https://github.com/yuliskov/SmartTube)
[![Discord](https://img.shields.io/badge/Discord-join-1E2A78?style=flat-square&logo=discord&logoColor=white)](https://discord.gg/xu3v6euSHq)

**[官网](https://newtube.org/) · [下载](#下载) · [功能](#功能) · [登录方式](#登录方式) · [常见问题](#常见问题) · [翻译](#翻译) · [致谢](#基于-smarttube)**

**[English](README.md) | 简体中文**

<br>

<img src=".github/assets/hero.webp" width="100%" alt="Three NewTube screens: the sign-in code, the watch page playing Sintel, and the Downloads tab">

</div>

> [!NOTE]
> **NewTube 底下全是 SmartTube 的功劳。**与 YouTube 通信的引擎、账号登录、
> SponsorBlock、DeArrow 和 Return YouTube Dislike 集成全部来自
> [SmartTube](https://github.com/yuliskov/SmartTube)。NewTube 加的是触屏界面、自己的
> 播放器和离线保存。它是独立项目，未经 SmartTube 开发者背书。
> NewTube 不接受捐赠：如果觉得有用，请[支持 SmartTube](https://github.com/yuliskov/SmartTube#donation)。

## 功能

<table>
<tr>
<td width="50%" valign="top">

#### 几秒搞定账号
用代码登录一次，订阅、历史、播放列表、稍后观看和推荐就从你的账号载入。
不需要 microG、GmsCore、root 或修改版应用。登录可选；不登录时，YouTube 也会
按你的观看内容个性化首页。

</td>
<td width="50%" valign="top">

#### 一直播
灭屏后台播放、画中画，还有可下拉、边看边逛的迷你播放器。锁屏和耳机线控也能用。

</td>
</tr>
<tr>
<td valign="top">

#### 离线保存
最高 1080p 任选画质，或只留音频。保存的视频在同一个播放器里离线播放，
也会出现在相册里。

</td>
<td valign="top">

#### 测出来的快
首屏约 0.24 秒出现，点相关视频约半秒出首帧（[Pixel 9 中位数](#有多快)）。
界面是朴素的 Material Design，有浅色和深色。

</td>
</tr>
<tr>
<td valign="top">

#### 少打扰
**SponsorBlock** 跳过赞助片段，**DeArrow** 换掉标题党标题和缩略图，
**Return YouTube Dislike** 显示预估的踩数。没有 Shorts。

</td>
<td valign="top">

#### 搭配电视上的 SmartTube
用遥控器代码与电视上的 SmartTube 配对。也可以从手机直接投到
Chromecast（该模式无直播、无字幕），或用电视自带的 YouTube 应用。

</td>
</tr>
</table>

<details>
<summary><b>还有这些</b></summary>

- 视频和手机允许时最高 4K（默认 1080p），可选编码
- 字幕，含自动翻译轨道，可调字幕样式和大小
- 0.25x–2x 倍速，步进更细
- 播放列表：全部播放、随机播放、保存到播放列表、新建播放列表、带“正在播放：…”的队列
- 多账号一键切换，也可一个都不用
- Android 8 及以上支持画中画
- 滑动手势：上滑进全屏、下滑退出、两侧调亮度和音量、横滑快进快退
- 短页设置，带搜索
- 评论可读可写，有实时聊天；章节和配音音轨
- 隧道和信号死角里会等待重试，出来后接着播
- 简体中文、英语和西班牙语

</details>

<p align="center">
<img src=".github/assets/demo.webp" width="23%" alt="NewTube in motion: Blender Studio's channel, a video with chapters, the comments panel, a seek and the mini-player">
<img src=".github/assets/screens/comments.webp" width="23%" alt="Comments panel under the video">
<img src=".github/assets/screens/mini.webp" width="23%" alt="Mini-player over a channel page">
<img src=".github/assets/screens/light.webp" width="23%" alt="The watch page in the light theme">
</p>

## 下载

<a href="https://github.com/aleixrodriala/newtube/releases/latest"><img src="images/badge_github.png" height="64" alt="Get it on GitHub"></a>

| 机型 | 文件 |
|:--|:--|
| 近 ~8 年的绝大多数手机 | `NewTube_<version>_arm64-v8a.apk` |
| 老 32 位手机 | `NewTube_<version>_armeabi-v7a.apk` |
| 不确定（任何 ARM 手机） | `NewTube_<version>_universal.apk`（更大） |
| 老 x86 设备和模拟器 | `NewTube_<version>_x86.apk` |

- **要求 Android 7.0 及以上。**NewTube 包名独立
  (`io.github.aleixrodriala.arc`)，可与 SmartTube 或 YouTube 应用共存。
- **更新：**用 [Obtainium](https://obtainium.imranr.dev) 时选 *Add app* 并粘贴
  `https://github.com/aleixrodriala/newtube`，或直接装新 APK 覆盖旧版。NewTube
  自己也会检查新版本，并在应用内提示。
- **验明你装的东西。**每个 APK 都用同一密钥签名。证书 SHA-256 是
  `2e:f9:9d:76:ed:fa:d9:88:ad:17:cd:ee:8b:a1:8c:63:4e:23:0f:e1:e3:cb:1f:dc:6c:db:02:49:37:0a:36:c9`。
  用 `apksigner verify --print-certs <file>.apk` 核对。每个版本还给每个文件列了 SHA-256。
  从 1.10.3 起 APK 由 GitHub Actions 从打 tag 的源码构建；用
  `gh attestation verify <file>.apk -R aleixrodriala/newtube` 核验。
  NewTube 的密钥与 SmartTube 不同，两个应用不能互相更新。
- **只在 GitHub 分发**，不上 Google Play。

## 登录方式

1. 打开**你**标签页，点账号行，再点**登录**。NewTube 会显示一个短代码。
2. 点**使用 Google 继续**。会打开 Google 自己的页面：选账号并允许访问。
   页面可能提到电视，因为 NewTube 的登录方式和电视一样。
3. 回来。登录会自动完成。

密码只会在 Google 页面里输入，绝不会输入到 NewTube。登录令牌存在你的手机上，
绝不发给开发者。可随时到
[myaccount.google.com/security](https://myaccount.google.com/security) 的
*您的第三方应用和服务连接*下撤销。

## 有多快

中位数，Pixel 9，2026 年 9 月 25–26 日测得，用的是后来成为 1.10.1 的代码的
release 包，每格 2–8 次：样本小、一部手机、一个运营商，移动数据还是分天测的。
后来的版本没再这样测过，1.12.0 还改了打开方式（首页先显示加载占位）。方法和
完整表格见 [STATUS.md](docs/mobile-port/STATUS.md)。

| | Wi-Fi | 移动数据 |
|:--|--:|--:|
| 打开应用 → 首屏 | 0.24 s | 0.24 s |
| 打开应用 → 首页完全绘制 | 1.35 s | 1.56 s |
| 点相关视频 → 首帧 | 0.50 s | 0.53 s |
| 点相关视频 → 看到画面 | 0.66 s | 0.73 s |
| 打开看到一半的视频 → 出画面 | 0.37 s | – |
| 点分享链接 → 首帧 | 0.66 s | 0.84 s |

## NewTube 和 SmartTube

| | SmartTube | NewTube |
|:--|:--|:--|
| 适合 | Android 电视和电视盒子 | 手机和平板 |
| 操作 | 电视遥控器（方向键） | 触屏和手势 |
| 界面 | Leanback | Material Design |
| 引擎 | MediaServiceCore + SharedModules | 同样模块的 fork |
| 播放器 | ExoPlayer | androidx Media3 |
| 离线保存 | 无 | 有 |
| 许可证 | MIT | MIT |

有 Android 电视？装 [SmartTube](https://github.com/yuliskov/SmartTube)。它很棒，
NewTube 还能投屏过去。

## 常见问题

<details>
<summary><b>SmartTube 能装到手机上吗？</b></summary>

SmartTube 是给 Android 电视和电视盒子做的，它的 README 写了不会出手机版。
NewTube 是基于 SmartTube 引擎的非官方手机客户端，有触屏界面、自己的播放器和
离线保存。它是独立项目，未经 SmartTube 开发者背书。

</details>

<details>
<summary><b>需要 microG、GmsCore、root 或 ReVanced 吗？</b></summary>

都不需要。NewTube 不是修改版 YouTube 应用。它像电视一样用代码登录，所以
不需要 Google Play 服务，也不需要替代品。

</details>

<details>
<summary><b>和 NewPipe、LibreTube、ReVanced 有什么区别？</b></summary>

都是好应用，选哪个看你要什么。NewPipe 和 LibreTube 不登录 Google 账号；
订阅存在手机上或 Piped 服务器上。ReVanced 是给官方应用打补丁，不 root 就要
GmsCore。NewTube 基于 SmartTube，所以直接用你的真实账号，两样都不需要。

</details>

<details>
<summary><b>还有别的基于 SmartTube 的手机应用吗？</b></summary>

有。[SmarterTube](https://github.com/CodeSculptor/SmarterTube) 是另一个独立的
SmartTube 手机 fork，值得对比。两者是各自独立的项目。今天（2026 年 9 月）的
一个区别：NewTube 能下载视频离线看。

</details>

<details>
<summary><b>安全吗？我怎么知道 APK 真的是 NewTube？</b></summary>

1.10.2 及以前的 APK 在维护者的电脑上构建。从 1.10.3 起由
GitHub Actions 从打 tag 的源码构建，每个文件都有构建证明，可用
`gh attestation verify <file>.apk -R aleixrodriala/newtube` 核验。构建还不可复现。
对照[下载](#下载)下的指纹核对签名证书，对照版本说明里的
SHA-256 核对每个文件。每个版本都打了 tag，所以能读到精确的源码。
应用无统计、无崩溃上报、无广告 SDK，开发者收不到任何东西。
它只和 YouTube（会看到你看什么，和在哪看一样）、可关闭的社区服务
（SponsorBlock、DeArrow、Return YouTube Dislike）以及 GitHub 通信。细节见
[PRIVACY.md](PRIVACY.md)。每项检查和链接，官网的
[信任与验证](https://newtube.org/#trust）下也有。

</details>

<details>
<summary><b>这是 AI 做的吗？</b></summary>

很大程度上是。NewTube 是一个人的项目，它自己的代码（手机界面、播放器和网络
部分）大多是用 AI 编程助手（Claude 和 Codex）写的；提交记录里有说明。维护者
主导并 review 这些工作，每天自己也在用。每个[版本记录](docs/releases/)都列了
哪些在真机上验过、哪些只跑了自动化测试、哪些还没做。底下的引擎是 SmartTube
的，由 @yuliskov 及其贡献者写了好几年。

</details>

<details>
<summary><b>为什么不接受捐赠？</b></summary>

NewTube 不接受捐赠。重活都是 SmartTube 干的，想回馈的话请
[支持 SmartTube](https://github.com/yuliskov/SmartTube#donation)。

</details>

<details>
<summary><b>视频播不了怎么办？</b></summary>

YouTube 有时会拒绝某个视频或账号。NewTube 会换路重试，但不是每个视频都救得回来。
如果一直失败，请[开 issue](https://github.com/aleixrodriala/newtube/issues/new/choose)，
附上视频链接和你的 NewTube 版本。

</details>

## 基于 SmartTube

| 项目 | 作者 | 用途 |
|:--|:--|:--|
| [SmartTube](https://github.com/yuliskov/SmartTube) | [@yuliskov](https://github.com/yuliskov) 及贡献者 | 引擎、账号和集成：底下的一切 |
| [SponsorBlock](https://sponsor.ajay.app) · [DeArrow](https://dearrow.ajay.app) | Ajay Ramachandran 及贡献者 | 片段和标题数据（CC BY-NC-SA 4.0） |
| [Return YouTube Dislike](https://returnyoutubedislike.com) | RYD 贡献者 | 踩数 |
| [androidx Media3](https://developer.android.com/media/media3) | Google（Apache-2.0） | 播放 |

完整声明见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
SmartTube 原 README 留在 [docs/UPSTREAM_README_SmartTube.md](docs/UPSTREAM_README_SmartTube.md)。

## 翻译

手机界面目前有英语、西班牙语和简体中文（种子翻译）。可以在浏览器里上
**[Weblate](https://hosted.weblate.org/projects/newtube/)** 参与翻译（WEBLATE-URL-PLACEHOLDER：
项目获批后页面生效），不用写代码，也不用 GitHub 账号。从 SmartTube 继承的
设置和文案已有约 45 种语言，只需补缺口。Weblate 会把翻译以 PR 发到这里，
随下一版发布。怎么参与、先译什么：[TRANSLATING.md](TRANSLATING.md)。

## 构建与贡献

新版本、求助和想法都在 [NewTube Discord](https://discord.gg/xu3v6euSHq)。
欢迎 issue 和 PR。构建方法见 [docs/BUILDING.md](docs/BUILDING.md)。
[更新日志](CHANGELOG.md)记录每个版本，测试者还有[西语版](CHANGELOG.es.md)。

## 许可证

[MIT](LICENSE)，与 SmartTube 相同。© yuliskov（SmartTube）及 NewTube 贡献者。

## Star 趋势

<a href="https://www.star-history.com/#aleixrodriala/newtube&Date">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=aleixrodriala/newtube&type=Date&theme=dark">
    <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/svg?repos=aleixrodriala/newtube&type=Date">
    <img alt="NewTube's GitHub stars over time" src="https://api.star-history.com/svg?repos=aleixrodriala/newtube&type=Date" width="100%">
  </picture>
</a>

---

*NewTube 是独立的非官方项目，与 Google LLC、YouTube 或 SmartTube 开发者均无关联，
未获资助、授权或背书，也不托管任何内容。请只在法律和平台条款允许的范围内保存。
YouTube、Android 和 Google 是 Google LLC 的商标。*
