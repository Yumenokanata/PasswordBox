package indi.yume.app.passwordbox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by bush2 on 2016/4/4.
 */
@Table(database = MainDataBase.class, name = "date_table")
public class DataTable extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private String title;

    @Column
    private String website;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String note;

    @Column
    private int labelColor = 0xff19b3b6;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public static DataTable getDataById(long id) {
        return new Select()
                .from(DataTable.class)
                .where(DataTable_Table.id.eq(id))
                .querySingle();
    }

    public static List<DataTable> getAllData() {
        return new Select()
                .from(DataTable.class)
                .queryList();
    }

    public DataTable() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.website);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.note);
        dest.writeInt(this.labelColor);
    }

    protected DataTable(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.website = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.note = in.readString();
        this.labelColor = in.readInt();
    }

    public static final Parcelable.Creator<DataTable> CREATOR = new Parcelable.Creator<DataTable>() {
        @Override
        public DataTable createFromParcel(Parcel source) {
            return new DataTable(source);
        }

        @Override
        public DataTable[] newArray(int size) {
            return new DataTable[size];
        }
    };
}
