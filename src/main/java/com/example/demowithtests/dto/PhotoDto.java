package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public class PhotoDto {
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String linkToPhoto;

    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public double x;

    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public double y;

    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public Date createdDate = Date.from(Instant.now());
}
