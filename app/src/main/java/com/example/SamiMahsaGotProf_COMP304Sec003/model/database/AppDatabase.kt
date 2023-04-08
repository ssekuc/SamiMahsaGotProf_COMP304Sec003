package com.example.SamiMahsaGotProf_COMP304Sec003.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.SamiMahsaGotProf_COMP304Sec003.model.Landmark
import com.example.SamiMahsaGotProf_COMP304Sec003.model.LandmarkType
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Landmark::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun landmarkDao(): LandmarkDao

    companion object {
        private const val DATABASE_NAME = "landmark_db"
        private var DATABASE_INSTANCE: AppDatabase? = null

        private val LANDMARK_NAME_LIST = arrayListOf(
            // Museums
            "Aga Khan Museum",
            "Royal Ontario Museum",
            "Art Gallery of Ontario",
            // Attractions
            "St Lawrence Market",
            "High Park",
            "CN Tower",
            // Old Buildings
            "Casa Loma",
            "Gooderham Building",
            "Fairmont Royal York"
        )

        // Coordinates for the landmarks
        private val LANDMARK_COORDINATES_LIST = arrayListOf(
            // Museums
            LatLng(43.725438158707014, -79.33222310186841),
            LatLng(43.672822532032065, -79.39468102804582),
            LatLng(43.6537851185458, -79.39249084419751),
            // Attractions
            LatLng(43.64946421808076, -79.37197455346465),
            LatLng(43.64673420301409, -79.46361520001467),
            LatLng(43.64272145936423, -79.38709971721751),
            // Old Buildings
            LatLng(43.678657851717624, -79.40948681534647),
            LatLng(43.64923787884328, -79.37508854548042),
            LatLng(43.646465058596505, -79.38104467722829)
        )

        // Addresses for the landmarks
        private val LANDMARK_ADDRESS_LIST = arrayListOf(
            // Museums
            "77 Wynford Dr, North York, ON M3C 1K1",
            "100 Queens Park, Toronto, ON M5S 2C6, Canada",
            "317 Dundas St W, Toronto, ON M5T 1G4",
            // Attractions
            "93 Front St E, Toronto, ON M5E 1C3",
            "1873 Bloor St W, Toronto, ON M6R 2Z3",
            "290 Bremner Blvd Toronto ON M5V 3L9",
            // Old Buildings
            "1 Austin Terrace Toronto ON M5R 1X8",
            "49 Wellington St E, Toronto, ON M5E 1C9, Canada",
            "100 Front St W, Toronto, ON M5J 1E3    "
        )

        fun getDatabase(context: Context): AppDatabase {
            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                DATABASE_INSTANCE = instance

                isDatabaseEmpty(instance) { isEmpty ->
                    if (isEmpty) {
                        informationIntoDatabase(instance)
                    }
                }
                instance
            }
        }

        private fun isDatabaseEmpty(
            database: AppDatabase,
            result: (isEmpty: Boolean) -> Unit
        ) {
            // This is not a good flow to checking database. but for this PRACTICE it is ok!
            CoroutineScope(Dispatchers.IO).launch {
                database.landmarkDao().getAllLandmarks().collect {
                    result.invoke(it.isEmpty())
                }
            }
        }

        // Function to put information into the database
        private fun informationIntoDatabase(database: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                LANDMARK_NAME_LIST.forEachIndexed { index, name ->
                    val landmark = Landmark(
                        place_name = name,
                        place_address = LANDMARK_ADDRESS_LIST[index],
                        place_type = getTypeFromIndex(index),
                        place_latitude = LANDMARK_COORDINATES_LIST[index].latitude,
                        place_longitude = LANDMARK_COORDINATES_LIST[index].longitude
                    )
                    database.landmarkDao().insertLandmark(landmark)
                }
            }
        }

        // Function to decide how we will insert information to the database
        private fun getTypeFromIndex(index: Int): LandmarkType {
            return when (index) {
                in 0..2 -> LandmarkType.MUSEUMS
                in 3..5 -> LandmarkType.ATTRACTIONS
                in 6..8 -> LandmarkType.OLD_BUILDING
                else -> LandmarkType.MUSEUMS // Safety net
            }
        }
    }
}