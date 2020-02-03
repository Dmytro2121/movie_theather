package com.dev.cinema.service.impl;

import java.util.List;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;
import mate.hibernate.cinema.lib.Inject;
import mate.hibernate.cinema.lib.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Inject
    private static MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public List<Movie> getAll() throws DataProcessingException {
        return movieDao.getAll();
    }


}