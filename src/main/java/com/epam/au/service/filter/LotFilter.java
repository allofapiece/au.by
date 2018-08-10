package com.epam.au.service.filter;

import com.epam.au.entity.lot.Lot;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * https://stackoverflow.com/questions/13400075/reflection-generic-get-field-value
 */
public class LotFilter implements EntityFilterInterface {
    private static final Logger LOG = Logger.getLogger(LotFilter.class);

    @Override
    public List<Lot> filter(Collection entities, Map<String, Criteria> conditions) {
        List<Lot> lots = (ArrayList) entities;

        for (Map.Entry<String, Criteria> entry : conditions.entrySet())
        {
            Iterator<Lot> i = lots.iterator();
            while (i.hasNext()) {
                Lot lot = i.next();
                try {
                    Field field = lot.getClass().getSuperclass().getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    Object value = field.get(lot);

                    Criteria criteria = entry.getValue();
                    switch (criteria.getMode()) {
                        case "=":
                            if (!criteria.getValues().contains(value)) {
                                i.remove();
                            }
                            break;

                        case "!=":
                            if (criteria.getValues().contains(value)) {
                                i.remove();
                            }
                            break;
                    }
                } catch (NoSuchFieldException e) {
                    LOG.error("Filter field error", e);
                } catch (IllegalAccessException e) {
                    LOG.error("Access error", e);
                }

            }
        }

        return lots;
    }
}
