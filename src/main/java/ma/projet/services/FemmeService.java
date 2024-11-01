package ma.projet.services;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;

import java.util.List;

public class FemmeService implements IDao<Femme> {
    @Override
    public boolean create(Femme femme) {
        return false;
    }

    @Override
    public Femme getById(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }
}
