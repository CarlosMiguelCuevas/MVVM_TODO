package dev.ccuevas.mvvmtraining.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ccuevas.mvvmtraining.data.TodoRepositoryImpl
import dev.ccuevas.mvvmtraining.data.database.TodoDatabase
import dev.ccuevas.mvvmtraining.domain.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(context: Application): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database.dao)
    }
}