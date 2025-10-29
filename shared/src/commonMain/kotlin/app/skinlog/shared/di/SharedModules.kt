package app.skinlog.shared.di

import org.koin.dsl.module

val sharedModule = module {

}

fun provideSharedModules() = listOf(sharedModule)