package com.example.galeria.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.galeria.di.ApplicationScope
import com.example.galeria.models.randomImageModel.Urls
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Urls::class], version = 1)
abstract class ImageUrlsDatabase : RoomDatabase(){

    abstract fun imageDao(): StoreImageUrlsDao

    class Callback @Inject constructor(
        private val database: Provider<ImageUrlsDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().imageDao()

            //wstawianie przykładowych danych do bazy typu image
            applicationScope.launch {
                dao.insert(Urls(
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=srgb&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=85",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=1080",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=400",
                    "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1649220340781-ffa14c5655e6",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=200"
                ))
                dao.insert(Urls(
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?crop=entropy&cs=srgb&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1&q=85",
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1",
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1&q=80&w=1080",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=400",
                    "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1649220340781-ffa14c5655e6",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=200"
                ))
                dao.insert(Urls(
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?crop=entropy&cs=srgb&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1&q=85",
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1",
                    "https://images.unsplash.com/photo-1648751597453-766f6930b622?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk2MTk&ixlib=rb-1.2.1&q=80&w=1080",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=400",
                    "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1649220340781-ffa14c5655e6",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=200"
                ))
                dao.insert(Urls(
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?crop=entropy&cs=srgb&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1&q=85",
                    "https://images.unsplash.com/photo-1648219776122-61024fefb9db?ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk0Mjg&ixlib=rb-1.2.1",
                    "https://images.unsplash.com/photo-1649368187303-bd3c3248e24b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTA0NDk2NTA&ixlib=rb-1.2.1&q=80&w=1080",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=400",
                    "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1649220340781-ffa14c5655e6",
                    "https://images.unsplash.com/photo-1649220340781-ffa14c5655e6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMjAwNTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTAyODgyODg&ixlib=rb-1.2.1&q=80&w=200"
                ))
            }
        }
    }
}