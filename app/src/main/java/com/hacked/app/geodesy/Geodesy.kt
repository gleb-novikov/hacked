package com.hacked.app.geodesy

/**
 * Класс для геодезических рассчётов.
 * @author Глеб Новиков
 * @version 1.0
 */
object Geodesy {
    /** Длина 1 градуса меридиана (в метрах).  */
    private const val LENGTH_LON = 111135

    /** Радиус Земли (в метрах).  */
    private const val RADIUS_EARTH = 6371000

    /**
     * Метод вычесления растояния между двумя объектами.
     * @param lat1 широта первого объекта
     * @param lon1 долгота первого объекта
     * @param lat2 широта второго объекта
     * @param lon2 долгота второго объекта
     * @return растояние между объектами в метрах
     */
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        /* Преобразование координат в радианную меру. */
        val lat1Rad = Math.toRadians(lat1)
        val lon1Rad = Math.toRadians(lon1)
        val lat2Rad = Math.toRadians(lat2)
        val lon2Rad = Math.toRadians(lon2)

        /* Вычисление ортодромии в радианной мере. */
        val orthodromicRad = Math.acos(
            Math.sin(lat1Rad) * Math.sin(lat2Rad) +
                    Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lon2Rad - lon1Rad)
        )

        /* Преобразование ортодромии в градусную меру. */
        val orthodromicDeg = Math.toDegrees(orthodromicRad)

        /* Вычисление длины ортодромии в метрах. */
        return orthodromicDeg * LENGTH_LON
    }

    /**
     * Метод вычисления угла между двумя объектами.
     * @param lat1 широта первого объекта
     * @param lon1 долгота первого объекта
     * @param lat2 широта второго объекта
     * @param lon2 долгота второго объекта
     * @return угол между объектами в градусах
     */
    fun getAngle(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        /* Преобразование координат в радианную меру. */
        val lat1Rad = Math.toRadians(lat1)
        val lon1Rad = Math.toRadians(lon1)
        val lat2Rad = Math.toRadians(lat2)
        val lon2Rad = Math.toRadians(lon2)

        /* Тайные знания. */
        val x = Math.cos(lat1Rad) * Math.sin(lat2Rad) -
                Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lon2Rad - lon1Rad)
        val y = Math.sin(lon2Rad - lon1Rad) * Math.cos(lat2Rad)
        val z = if (x < 0) Math.atan(-y / x) + Math.PI else Math.atan(-y / x)
        val c = (z + Math.PI) % (2 * Math.PI) - Math.PI

        /* Вычисление радианной меры угла. */
        val angleRad = 2 * Math.PI + 2 * Math.PI * Math.floor(c / (2 * Math.PI)) - c

        /* Преобразование угла в градусную меру. */
        return Math.toDegrees(angleRad)
    }

    /**
     * Метод вычисления координат результирующего объекта, расположенного на определённом
     * расстоянии и под нужным углом, относительно исходного объекта.
     * @param lat широта объекта
     * @param lon долгота объекта
     * @param distance растояние от объекта (в метрах)
     * @param angle направление относительно объекта (в градусах)
     * @return массив с координатами результирующего объекта (latitude, longitude)
     */
    fun coordinates(lat: Double, lon: Double, distance: Double, angle: Double): DoubleArray {
        /* Преобразование координат и угла в радианную меру. */
        val latRad = Math.toRadians(lat)
        val lonRad = Math.toRadians(lon)
        val angleRad = Math.toRadians(angle)

        /* Вычисление долготы и широты объекта в радианной мере. */
        val objectLatRad = Math.asin(
            Math.sin(latRad) * Math.cos(distance / RADIUS_EARTH) +
                    Math.cos(latRad) * Math.sin(distance / RADIUS_EARTH) * Math.cos(angleRad)
        )
        val objectLonRad = lonRad + Math.atan2(
            Math.sin(angleRad) * Math.sin(distance / RADIUS_EARTH) * Math.cos(latRad),
            Math.cos(distance / RADIUS_EARTH) - Math.sin(latRad) * Math.sin(latRad)
        )

        /* Преобразование координат в градусную меру. */
        val objectLatDeg = Math.toDegrees(objectLatRad)
        val objectLonDeg = Math.toDegrees(objectLonRad)

        /* Массив с координатами нужного объекта. */
        return doubleArrayOf(objectLatDeg, objectLonDeg)
    }
}