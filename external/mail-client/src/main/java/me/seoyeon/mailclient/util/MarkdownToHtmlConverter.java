package me.seoyeon.mailclient.util;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class MarkdownToHtmlConverter {

  /**
   * 마크다운을 HTML 로 변환한다
   *
   * @param markdown 마크다운 형식 문자열
   * @return HTML 형식 문자열
   */
  public String convert(String markdown) {

    //    markdown = addMermaidScriptIfPresent(markdown);

    Parser parser = Parser.builder().build();
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    return renderer.render(parser.parse(markdown));
  }

  //  private String addMermaidScriptIfPresent(String markdown) {
  //    // Mermaid 블록 감지 및 div 래핑
  //    if (!markdown.contains("```mermaid")) return markdown;
  //    markdown +=
  //        markdown.replaceAll("(?s)```mermaid\\n(.*?)\\n```", "<div class=\"mermaid\">$1</div>");
  //    // Mermaid.js 스크립트 추가
  //    markdown +=
  //        """
  //            <script type="module">
  //                import mermaid from
  // 'https://cdn.jsdelivr.net/npm/mermaid@10/dist/mermaid.esm.min.mjs';
  //                mermaid.initialize({ startOnLoad: true });
  //            </script>
  //        """;
  //    return markdown;
  //  }
}
