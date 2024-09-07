package com.better.Summariez.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleSearchResponseDTO {
    private String kind;
    private int totalItems;
    private List<Volume> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Volume {
        private String kind;
        private String id;
        private String etag;
        private String selfLink;
        private VolumeInfo volumeInfo;
        private SaleInfo saleInfo;
        private AccessInfo accessInfo;
        private SearchInfo searchInfo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String publisher;
        private String publishedDate;
        private String description;
        private List<IndustryIdentifier> industryIdentifiers;
        private ReadingModes readingModes;
        private int pageCount;
        private String printType;
        private List<String> categories;
        private double averageRating;
        private int ratingsCount;
        private String maturityRating;
        private boolean allowAnonLogging;
        private String contentVersion;
        private PanelizationSummary panelizationSummary;
        private ImageLinks imageLinks;
        private String language;
        private String previewLink;
        private String infoLink;
        private String canonicalVolumeLink;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IndustryIdentifier {
        private String type;
        private String identifier;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReadingModes {
        private boolean text;
        private boolean image;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PanelizationSummary{
        private boolean containsEpubBubbles;
        private boolean containsImageBubbles;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinks {
        private String smallThumbnail;
        private String thumbnail;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SaleInfo {
//        private String country;
//        private String saleability;
//        @JsonProperty("isEbook")
//        private boolean isEbook;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccessInfo {
        private String country;
        private String viewability;
        private boolean embeddable;
        private boolean publicDomain;
        private String textToSpeechPermission;
        private Epub epub;
        private Pdf pdf;
        private String webReaderLink;
        private String accessViewStatus;
        private boolean quoteSharingAllowed;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Epub {
        private boolean isAvailable;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pdf {
        private boolean isAvailable;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchInfo {
        private String textSnippet;
    }
}
