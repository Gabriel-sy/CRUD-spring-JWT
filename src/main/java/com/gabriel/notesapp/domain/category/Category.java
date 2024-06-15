package com.gabriel.notesapp.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    public Category(CategoryDTO data){
        this.categoryName = data.categoryName();
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
