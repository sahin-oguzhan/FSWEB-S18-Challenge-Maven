package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardRepositoryImpl implements CardRepository {
    private EntityManager entityManager;

    @Autowired
    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Card save(Card card) {
        CardValidation.cardCheck(card);
        entityManager.persist(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        CardValidation.textCheck(color, "Color");
        CardValidation.colorCheck(color);
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE color = :color", Card.class);
        query.setParameter("color", color);
        if (query.getResultList().isEmpty()) {
            throw new CardException("Card cannot find!", HttpStatus.NOT_FOUND);
        }
        return query.getResultList();
    }

    @Override
    public List<Card> findAll() {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c from Card c", Card.class);
        return query.getResultList();
    }

    @Override
    public List<Card> findByValue(int value) {
        CardValidation.valueValid(value);
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE value = :value", Card.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        CardValidation.textCheck(type, "Type");
        Type enumType = Enum.valueOf(Type.class, type.toUpperCase());
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE type = :type", Card.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Card update(Card card) {
        CardValidation.cardCheck(card);
        entityManager.merge(card);
        return card;
    }

    @Transactional
    @Override
    public Card remove(long id) {
        CardValidation.idValid(id);
        Card card = entityManager.find(Card.class, id);
        CardValidation.cardCheck(card);
        entityManager.remove(card);
        return card;
    }
}
