package com.example.applocation.domain.dto;

import com.example.applocation.domain.Massage;
import com.example.applocation.domain.User;
import com.example.applocation.domain.util.MessageHelper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

public class MassageDto {

    private Long id;
    private String text;
    private String tag;
    private String filename;
    private User author;
    private Long likes;
    private Boolean meLiked;

    public MassageDto(Massage massage, Long likes, Boolean meLiked) {
        this.likes = likes;
        this.meLiked = meLiked;
        this.id = massage.getId();
        this.text = massage.getText();
        this.tag = massage.getTag();
        this.filename = massage.getFilename();
        this.author = massage.getAuthor();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getFilename() {
        return filename;
    }

    public User getAuthor() {
        return author;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    public String getAuthorName(){
        return MessageHelper.getAuthorName(author);
    }

    @Override
    public String toString() {
        return "MassageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
