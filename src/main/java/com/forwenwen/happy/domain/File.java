package com.forwenwen.happy.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Table
@Entity
public class File {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy="uuid")
    private String id;
    @Column(name = "file_address")
    private String fileAddress;

    @Column(name = "create_time", columnDefinition = "timestamp default current_timestamp")
    private Date createTime;

    @Column(name = "file_upload_name" )
    private String fileUploadName;

    @Column(name = "file_save_name")
    private String fileSaveName;

    private Integer deleted = 0;

    public File() {}

    public File(String fileAddress, Date createTime, String fileUploadName, String fileSaveName, Integer deleted) {
        this.fileAddress = fileAddress;
        this.createTime = createTime;
        this.fileUploadName = fileUploadName;
        this.fileSaveName = fileSaveName;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", fileAddress='" + fileAddress + '\'' +
                ", createTime=" + createTime +
                ", fileUploadName='" + fileUploadName + '\'' +
                ", fileSaveName='" + fileSaveName + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileUploadName() {
        return fileUploadName;
    }

    public void setFileUploadName(String fileUploadName) {
        this.fileUploadName = fileUploadName;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
