package uz.dev.caveatemptor.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultLoadEventListener;
import org.hibernate.event.spi.LoadEvent;

public class CustomLoadEventListener extends DefaultLoadEventListener {

    @Override
    public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
//        System.out.println("Load recorded from " + getClass().getSimpleName()
//                + ", entity: " + event.getResult());
        super.onLoad(event, loadType);
    }
}
