package com.workintech.fswebs18challengemaven.entity;

import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
@Entity
@Table(name = "card", schema = "sprint18challenge")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer value;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Color color;

    public Card(Integer value, Type type, Color color) {
        if (type == Type.JOKER) {
            this.type = type;
            this.value = null;
            this.color = null;
        } else if (value != null && type != null) {
            throw new CardException("A card cannot have both a value and a type!", HttpStatus.BAD_REQUEST);
        } else if (value != null) {
            this.value = value;
            this.type = null;
            this.color = color;
        } else if (type != null) {
            this.type = type;
            this.value = null;
            this.color = color;
        } else {
            throw new CardException("Both value and type cannot be null!", HttpStatus.BAD_REQUEST);
        }
    }
}
