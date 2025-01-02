package me.seoyeon.growthbatch.service;

public record GrowthMemoItem(String title, String url) {
  public static GrowthMemoItem create(String title, String url) {
    return new GrowthMemoItem(title, url);
  }
}
