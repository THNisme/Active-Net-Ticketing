/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.nvd2603;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 *
 * @author NguyenDuc
 */
public class TicketPDFGenerator {

    public static File createTicketPDF(
            String outputDir,
            String eventName,
            String placeName,
            String startStr,
            String buyerName,
            String ticketType,
            String serialNumber
    ) throws Exception {

        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File pdfFile = new File(dir, "TICKET_" + serialNumber + ".pdf");
        PdfWriter writer = new PdfWriter(pdfFile);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4);
        doc.setMargins(36, 36, 36, 36);

        PdfFont font = PdfFontFactory.createFont("C:/Windows/Fonts/arial.ttf", PdfEncodings.IDENTITY_H);
        doc.setFont(font);

        doc.add(new Paragraph("E-TICKET")
                .setBold()
                .setFontSize(26)
                .setFontColor(ColorConstants.GREEN)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("\n"));

        doc.add(new Paragraph("Sự kiện: " + eventName).setBold().setFontSize(14));
        doc.add(new Paragraph("Địa điểm: " + placeName).setFontSize(13));
        doc.add(new Paragraph("Thời gian: " + startStr).setFontSize(13));
        doc.add(new Paragraph("Loại vé: " + ticketType).setFontSize(13));
        doc.add(new Paragraph("Mã Serial: " + serialNumber).setFontSize(13));
        doc.add(new Paragraph("Người mua: " + buyerName).setFontSize(13));

        doc.add(new Paragraph("\n──────────────────────────────────────────────\n")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY));

        BufferedImage qrImage = QRCodeGenerator.generateBufferedQR(serialNumber, 220, 220);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);

        Image qr = new Image(ImageDataFactory.create(baos.toByteArray()));
        qr.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qr.setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1));
        doc.add(qr);

        doc.add(new Paragraph("\nLưu ý:")
                .setBold()
                .setFontSize(13)
                .setFontColor(ColorConstants.GREEN));

        doc.add(new Paragraph(
                "- Mỗi vé chỉ dùng cho 1 người.\n"
                + "- Xuất trình mã QR này tại cổng soát vé.\n"
                + "- Vé không hoàn tiền sau khi thanh toán.")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT));

        doc.close();

        return pdfFile;
    
}
}