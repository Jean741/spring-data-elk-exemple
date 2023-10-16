package com.ondev.elk.model;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import java.util.Arrays;
import java.util.List;

import com.ondev.elk.model.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "blog"/*, type = "article"*/)
public class Article {

    @Id
    private String id;

    @MultiField(mainField = @Field(type = Text, fielddata = true), otherFields = { @InnerField(suffix = "verbatim", type = Keyword) })
    private String title;

    @Field(type = Nested, includeInParent = true)
    private List<Author> authors;

    @Field(type = Keyword)
    private String[] tags;

    public Article(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", authors=" + authors + ", tags=" + Arrays.toString(tags) + '}';
    }
}