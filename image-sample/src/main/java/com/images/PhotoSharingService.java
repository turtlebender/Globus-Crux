package com.images;

import org.globus.crux.service.EPRFactory;
import org.globus.crux.service.CreateState;
import org.globus.crux.service.Payload;

import javax.annotation.Resource;

/**
 * @author turtlebender
 */
public class PhotoSharingService {
    GalleryService galleryService;

    @CreateState
    @Payload(namespace = "http://images.com", localpart = "CreateGallery")
    public CreateGalleryResponse createGallery(CreateGallery request) {
        return new CreateGalleryResponse(galleryService.createGallery(request.getName()));
    }

    public FindGalleryResponse findGallery(FindGallery request) {
        Gallery gallery = galleryService.getGallery(request.getGalleryName());
        if (gallery == null) {
            //handle null resource
        }
        return new FindGalleryResponse().
                withEndpointReference(galleryService.findGallery(request.getGalleryName()));
    }

    public GalleryService getGalleryService() {
        return galleryService;
    }

    public void setGalleryService(GalleryService galleryService) {
        this.galleryService = galleryService;
    }
}