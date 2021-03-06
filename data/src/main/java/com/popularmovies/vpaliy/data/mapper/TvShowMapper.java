package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.popularmovies.vpaliy.data.utils.MapperUtils.convert;
import static com.popularmovies.vpaliy.data.utils.MapperUtils.convertToBackdrops;
import static com.popularmovies.vpaliy.data.utils.MapperUtils.convertToGenres;
import static com.popularmovies.vpaliy.data.utils.MapperUtils.convertToYear;

@Singleton
public class TvShowMapper extends Mapper<MediaCover,TvShow> {

    private ImageQualityConfiguration qualityConfiguration;

    @Inject
    public TvShowMapper(@NonNull ImageQualityConfiguration qualityConfiguration){
        this.qualityConfiguration=qualityConfiguration;
    }

    @Override
    public MediaCover map(TvShow tvShow) {
        MediaCover cover=new MediaCover();
        cover.setMediaId(tvShow.getId());
        cover.setAverageRate(tvShow.getVoteAverage().doubleValue());
        cover.setBackdrops(convert(tvShow.getBackdrops(),qualityConfiguration));
        cover.setMovieTitle(tvShow.getName());
        cover.setPosterPath(qualityConfiguration.convertCover(tvShow.getPosterPath()));
        cover.setMainBackdrop(qualityConfiguration.convertBackdrop(tvShow.getBackdropPath()));
        cover.setReleaseDate(tvShow.getFirstAirDate());
        cover.setGenres(convert(tvShow.getGenreList()));
        cover.setMustWatch(tvShow.isMustWatch());
        cover.setWatched(tvShow.isWatched());
        cover.setFavorite(tvShow.isFavorite());
        cover.setTvShow(true);
        cover.setReleaseDate(tvShow.getFirstAirDate());
        cover.setReleaseYear(convertToYear(tvShow.getFirstAirDate()));
        return cover;
    }

    @Override
    public TvShow reverseMap(MediaCover mediaCover) {
        TvShow tvShow=new TvShow();
        tvShow.setName(mediaCover.getMovieTitle());
        tvShow.setVoteAverage(mediaCover.getAverageVote());
        tvShow.setId(mediaCover.getMediaId());
        tvShow.setFirstAirDate(mediaCover.getReleaseDate());
        tvShow.setPosterPath(qualityConfiguration.extractPath(mediaCover.getPosterPath()));
        tvShow.setBackdrops(convertToBackdrops(mediaCover.getBackdrops(),qualityConfiguration));
        tvShow.setGenreList(convertToGenres(mediaCover.getGenres()));
        tvShow.setFavorite(mediaCover.isFavorite());
        tvShow.setWatched(mediaCover.isWatched());
        tvShow.setMustWatch(mediaCover.isMustWatch());
        tvShow.setBackdropPath(qualityConfiguration.extractPath(mediaCover.getMainBackdrop()));
        return tvShow;
    }
}
