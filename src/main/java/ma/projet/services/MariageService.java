package ma.projet.services;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;

import java.util.List;

public class MariageService implements IDao<Mariage> {
    @Override
    public boolean create(Mariage o) {
        return false;
    }

    @Override
    public Mariage getById(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }
}
