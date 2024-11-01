package ma.projet.services;

import ma.projet.beans.Homme;
import ma.projet.dao.IDao;

import java.util.List;

public class HommeService implements IDao<Homme> {
    @Override
    public boolean create(Homme homme) {
        return false;
    }

    @Override
    public Homme getById(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }
}
