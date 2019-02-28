# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# chipslayoutmanager
-keep class com.beloo.widget.chipslayoutmanager.** { *; }
-dontwarn com.beloo.widget.chipslayoutmanager.Orientation

# retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keep class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabas
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}




#reactivenetwork
-dontwarn com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
-dontwarn io.reactivex.functions.Function
-dontwarn rx.internal.util.**
-dontwarn sun.misc.Unsafe

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


# bitcoinj
-keep,includedescriptorclasses class org.bitcoinj.wallet.Protos$** { *; }
-keepclassmembers class org.bitcoinj.wallet.Protos { com.google.protobuf.Descriptors$FileDescriptor descriptor; }
-keep,includedescriptorclasses class org.bitcoin.protocols.payments.Protos$** { *; }
-keepclassmembers class org.bitcoin.protocols.payments.Protos { com.google.protobuf.Descriptors$FileDescriptor descriptor; }
-dontwarn org.bitcoinj.store.WindowsMMapHack
-dontwarn org.bitcoinj.store.LevelDBBlockStore
-dontnote org.bitcoinj.crypto.DRMWorkaround
-dontnote org.bitcoinj.crypto.TrustStoreLoader$DefaultTrustStoreLoader
-dontnote com.subgraph.orchid.crypto.PRNGFixes
-dontwarn okio.DeflaterSink
-dontwarn okio.Okio
-dontnote com.squareup.okhttp.internal.Platform
-dontwarn org.bitcoinj.store.LevelDBFullPrunedBlockStore**
-dontwarn org.bitcoinj.protocols.channels.PaymentChannelClient
-keep class org.bitcoinj.crypto.MnemonicCode { *; }

# zxing
-dontwarn com.google.zxing.common.BitMatrix

# Guava
-dontwarn sun.misc.Unsafe
-dontwarn java.lang.ClassValue
-dontwarn com.google.errorprone.annotations.**
-dontwarn afu.org.checkerframework.checker.**,org.checkerframework.checker.**
-dontnote com.google.common.reflect.**
-dontnote com.google.appengine.**
-dontnote com.google.apphosting.**
-dontnote com.google.common.hash.Striped64,com.google.common.hash.Striped64$Cell
-dontnote com.google.common.cache.Striped64,com.google.common.cache.Striped64$Cell
-dontnote com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper

# slf4j
-dontwarn org.slf4j.MDC
-dontwarn org.slf4j.MarkerFactory
-dontwarn org.slf4j.LoggerFactory

# app
-keepnames class io.imtouch.gligamesh.data.bean.** { *; }
-keepnames class io.imtouch.gligamesh.data.body.** { *; }
-keepnames class io.imtouch.rates.bean.** { *; }
-keepnames class io.imtouch.balance.data.** { *; }
-keepnames class io.imtouch.gligamesh.data.db.table.** { *; }
-keep class io.imtouch.gligamesh.ui.help.AutoLinkStyleTextView{ *; }
-keep class io.imtouch.gligamesh.ui.widget.BadgeActionProvider{ *; }
-keep class io.imtouch.gligamesh.presentation.transfer.FastTransferActivity$ViewWrapper{ *; }

-keep class com.tencent.android.tpush.** {* ;}
-keep class com.tencent.mid.** {* ;}
-keep class com.qq.taf.jce.** {*;}
-keep class com.tencent.bigdata.** {* ;}

#华为通道
#-keepattributes *Annotation*
#-keepattributes Exceptions
#-keepattributes InnerClasses
#-keepattributes Signature
#-keepattributes SourceFile,LineNumberTable
#-keep class com.hianalytics.android.**{*;}
#-keep class com.huawei.updatesdk.**{*;}
#-keep class com.huawei.hms.**{*;}

#小米通道
#-keep class com.xiaomi.**{*;}
#-keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver

#魅族通道
#-dontwarn com.meizu.cloud.pushsdk.**
#-keep class com.meizu.cloud.pushsdk.**{*;}


-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep class com.google.android.gms.measurement.AppMeasurement { *; }
-keep class com.google.android.gms.measurement.AppMeasurement$OnEventListener { *; }

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


-dontwarn com.avos.**
-keep class com.avos.** { *;}


#jpush
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#MPAndroidChart
-dontwarn com.github.mikephil.**
-keep public class com.github.mikephil.** {
     public protected *;
}
-keep public class com.github.mikephil.charting.animation.* {
    public protected *;
}

-keep class com.alibaba.fastjson.** { *; }
-dontwarn  com.alibaba.fastjson.**

-keep class com.lambdaworks.** { *; }

-ignorewarnings

# MeiZuFingerprint
-keep class com.fingerprints.service.** { *; }

# SmsungFingerprint
-keep class com.samsung.android.sdk.** { *; }

# BaseQuickAdapter
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
