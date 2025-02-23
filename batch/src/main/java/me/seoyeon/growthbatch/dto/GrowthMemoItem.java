package me.seoyeon.growthbatch.dto;

public record GrowthMemoItem(String title, String url) {
  public static GrowthMemoItem create(String title, String url) {
    return new GrowthMemoItem(title, url);
  }
}
