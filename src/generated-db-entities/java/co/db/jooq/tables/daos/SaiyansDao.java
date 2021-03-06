/*
 * This file is generated by jOOQ.
 */
package co.db.jooq.tables.daos;


import co.db.jooq.tables.Saiyans;
import co.db.jooq.tables.records.SaiyansRecord;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class SaiyansDao extends DAOImpl<SaiyansRecord, co.db.jooq.tables.pojos.Saiyans, Integer> {

    /**
     * Create a new SaiyansDao without any configuration
     */
    public SaiyansDao() {
        super(Saiyans.SAIYANS, co.db.jooq.tables.pojos.Saiyans.class);
    }

    /**
     * Create a new SaiyansDao with an attached configuration
     */
    @Autowired
    public SaiyansDao(Configuration configuration) {
        super(Saiyans.SAIYANS, co.db.jooq.tables.pojos.Saiyans.class, configuration);
    }

    @Override
    public Integer getId(co.db.jooq.tables.pojos.Saiyans object) {
        return object.getSaiyanId();
    }

    /**
     * Fetch records that have <code>saiyan_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchRangeOfSaiyanId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Saiyans.SAIYANS.SAIYAN_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>saiyan_id IN (values)</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchBySaiyanId(Integer... values) {
        return fetch(Saiyans.SAIYANS.SAIYAN_ID, values);
    }

    /**
     * Fetch a unique record that has <code>saiyan_id = value</code>
     */
    public co.db.jooq.tables.pojos.Saiyans fetchOneBySaiyanId(Integer value) {
        return fetchOne(Saiyans.SAIYANS.SAIYAN_ID, value);
    }

    /**
     * Fetch records that have <code>saiyan_name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchRangeOfSaiyanName(String lowerInclusive, String upperInclusive) {
        return fetchRange(Saiyans.SAIYANS.SAIYAN_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>saiyan_name IN (values)</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchBySaiyanName(String... values) {
        return fetch(Saiyans.SAIYANS.SAIYAN_NAME, values);
    }

    /**
     * Fetch records that have <code>saiyan_gender BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchRangeOfSaiyanGender(String lowerInclusive, String upperInclusive) {
        return fetchRange(Saiyans.SAIYANS.SAIYAN_GENDER, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>saiyan_gender IN (values)</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchBySaiyanGender(String... values) {
        return fetch(Saiyans.SAIYANS.SAIYAN_GENDER, values);
    }

    /**
     * Fetch records that have <code>saiyan_blood BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchRangeOfSaiyanBlood(String lowerInclusive, String upperInclusive) {
        return fetchRange(Saiyans.SAIYANS.SAIYAN_BLOOD, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>saiyan_blood IN (values)</code>
     */
    public List<co.db.jooq.tables.pojos.Saiyans> fetchBySaiyanBlood(String... values) {
        return fetch(Saiyans.SAIYANS.SAIYAN_BLOOD, values);
    }
}
