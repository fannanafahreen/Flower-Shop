package fannana.fahreen.floresdejardin.repository.shared_pref

import android.content.Context
import android.content.SharedPreferences

class PrefUtils private constructor(){

    companion object{



        const val LOGGED_IN = "log_no"
        const val SIGNED_IN = "signed_in_no"
        const val ORDERED = "ordered"

        const val PREF_USER_NAME = "doj"
        const val PREF_USER_MOBILE = "mobile"
        const val PREF_USER_ADDRESS = "bg"

        const val PREF_PAY_METHOD = "None"

        const val PREF_REMEMBER_ME = "RememberMe"
        const val REMEMBER_ME_YES = "YES"
        const val REMEMBER_ME_NO = "NO"

        const val PREF_LANGUAGE = "Language"
        const val BN = "bn"
        const val EN = "en"



        private lateinit var sharedpreferences: SharedPreferences
        private const val PREFERENCE_NAME = "FlowerShop"
        private lateinit var instance : PrefUtils

        fun init(context: Context) : PrefUtils {
            sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            if(!this::instance.isInitialized){
                instance = PrefUtils()
            }
            return instance
        }
    }

    fun saveStringData(key: String?, value: String?) {
        val editor = sharedpreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringData( key: String?): String? {
        return if (sharedpreferences.contains(key)) {
            sharedpreferences.getString(key, "")
        } else ""
    }

    fun saveDoubleData(key: String?, value: Double) {
        val editor = sharedpreferences.edit()
        editor.putFloat(key.toString(), value.toFloat())
        editor.apply()
    }

    fun getDoubleData(key: String?): Double {
        return if (sharedpreferences.contains(key)) {
            sharedpreferences.getFloat(key, 0F).toDouble()
        } else 0.0
    }

    fun saveBoolData( key: String?, value: Boolean) {
        val editor = sharedpreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolData(key: String?): Boolean {
        return if (sharedpreferences.contains(key)) {
            sharedpreferences.getBoolean(key, false)
        } else false
    }

    fun saveIntData( key: String?, value: Int) {
        val editor = sharedpreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntData(key: String?): Int {
        return if (sharedpreferences.contains(key)) {
            sharedpreferences.getInt(key, 0)
        } else 0
    }


}