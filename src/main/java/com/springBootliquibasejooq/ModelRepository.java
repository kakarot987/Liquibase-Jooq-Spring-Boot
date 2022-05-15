package com.springBootliquibasejooq;

import co.db.jooq.tables.Saiyans;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelRepository {

    private final DSLContext dslContext;

    public ModelRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void addSaiyan(int id  ,String name, String gender, String blood) {

        dslContext.insertInto(Saiyans.SAIYANS)
                .set(Saiyans.SAIYANS.SAIYAN_ID,id)
                .set(Saiyans.SAIYANS.SAIYAN_NAME,name)
                .set(Saiyans.SAIYANS.SAIYAN_GENDER,gender)
                .set(Saiyans.SAIYANS.SAIYAN_BLOOD, blood)
                .execute();
    }

    public List<co.db.jooq.tables.pojos.Saiyans> selectAllSaiyans(){
        return dslContext.selectFrom(Saiyans.SAIYANS)
                .fetchInto(co.db.jooq.tables.pojos.Saiyans.class);
    }

}
