package indi.yume.view.appkotlin.database

import android.os.Parcel
import android.os.Parcelable

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.structure.BaseModel

/**
 * Created by bush2 on 2016/4/4.
 */
@Table(database = MainDataBase::class, name = "date_table")
class DataTable : BaseModel, Parcelable {

    @Column
    @PrimaryKey(autoincrement = true)
    var id: Long? = null

    @Column
    var title: String? = null

    @Column
    var website: String? = null

    @Column
    var username: String? = null

    @Column
    var password: String? = null

    @Column
    var note: String? = null

    @Column
    var labelColor = 0xff19b3b6.toInt()

    constructor() {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(this.id)
        dest.writeString(this.title)
        dest.writeString(this.website)
        dest.writeString(this.username)
        dest.writeString(this.password)
        dest.writeString(this.note)
        dest.writeInt(this.labelColor)
    }

    protected constructor(`in`: Parcel) {
        this.id = `in`.readValue(Long::class.java.classLoader) as Long
        this.title = `in`.readString()
        this.website = `in`.readString()
        this.username = `in`.readString()
        this.password = `in`.readString()
        this.note = `in`.readString()
        this.labelColor = `in`.readInt()
    }

    companion object {

        fun getDataById(id: Long): DataTable {
            return Select().from(DataTable::class.java).where(DataTable_Table.id.eq(id)).querySingle()
        }

        val allData: List<DataTable>
            get() = Select().from(DataTable::class.java).queryList()

        val CREATOR: Parcelable.Creator<DataTable> = object : Parcelable.Creator<DataTable> {
            override fun createFromParcel(source: Parcel): DataTable {
                return DataTable(source)
            }

            override fun newArray(size: Int): Array<DataTable?> {
                return arrayOfNulls(size)
            }
        }
    }
}
