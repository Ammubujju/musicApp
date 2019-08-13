package com.stackroute.service;

import com.stackroute.domain.Track;
import com.stackroute.exception.TrackAlreadyExistException;
import com.stackroute.exception.TrackNotFoundException;
import com.stackroute.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {
    TrackRepository trackRepository;

    // Providing implementation for all methods of track
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistException {
        if (trackRepository.existsById(track.getId())) {
            throw new TrackAlreadyExistException("Track Already exist");
        }
        Track savetrack = trackRepository.save(track);

        if (savetrack == null) {
            throw new TrackAlreadyExistException("Track already present");
        }
        return savetrack;
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public Track updateTrack(Track track) throws TrackNotFoundException {
       // Track track1 = new Track();
        if (trackRepository.existsById(track.getId())) {
            track.setName(track.getName());
            trackRepository.save(track);
            System.out.println(track);
            return track;
        } else {
            throw new TrackNotFoundException("Track not found");
        }
    }


    /* public Track deleteTrack(int id)
     {
         Track result;
         if(trackRepository.existsById(id))
         {
             trackRepository.deleteById(id);

         }
         else
         {
             result=false;
         }
         return Track;
     }
 }*/
    @Override
    public Track deleteTrack(int id) throws TrackNotFoundException {
        Optional<Track> track1 = trackRepository.findById(id);

        if (!track1.isPresent()) {
            throw new TrackNotFoundException("Track Not Found");
        }

        try {

            trackRepository.delete(track1.get());

            return track1.get();

        } catch (Exception exception) {
            return null;
        }
    }
}


