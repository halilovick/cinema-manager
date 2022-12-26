package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Karta;
import ba.unsa.etf.rpr.exceptions.FilmoviException;

import java.util.List;

public class karteManager {
    public Karta add(Karta k) throws FilmoviException {
        try {
            return DaoFactory.kartaDao().add(k);
        } catch (FilmoviException e) {
            throw new FilmoviException(e.getMessage(), e);
        }
    }

    public void delete(int kartaId) throws FilmoviException {
        try {
            DaoFactory.kartaDao().delete(kartaId);
        } catch (FilmoviException e) {
            throw new FilmoviException(e.getMessage(), e);
        }
    }

    public Karta update(Karta karta) throws FilmoviException {
        return DaoFactory.kartaDao().update(karta);
    }

    public List<Karta> getAll() throws FilmoviException {
        return DaoFactory.kartaDao().getAll();
    }

    public Karta getById(int id) throws FilmoviException {
        return DaoFactory.kartaDao().getById(id);
    }

    public void deleteAll() throws FilmoviException {
        DaoFactory.kartaDao().deleteAll();
    }

    public void deleteWithFilmId(int id) throws FilmoviException {
        DaoFactory.kartaDao().deleteWithFilmId(id);
    }
}