package controler;
/*
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.font.FontProvider;
//import com.itextpdf.licensekey.LicenseKey;
//import com.itextpdf.samples.pdfHTML.AccessiblePDF.HeaderTagging.AccessibilityTagWorkerFactory;
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 

public class PDF {
	/*
    //License key path
    public static final String LICENSE = "src/test/resources/pdfHTML/itextkey-html2pdf_typography.xml";
    
    public void createPdf(String src, String dest, String resources) throws IOException {
        try {
            FileOutputStream outputStream = new FileOutputStream(dest);
 
            WriterProperties writerProperties = new WriterProperties();
            //Add metadata
            writerProperties.addXmpMetadata();
 
            PdfWriter pdfWriter = new PdfWriter(outputStream, writerProperties);
 
            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            pdfDoc.getCatalog().setLang(new PdfString("en-US"));
            //Set the document to be tagged
            pdfDoc.setTagged();
            pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
 
            //Set meta tags
            PdfDocumentInfo pdfMetaData = pdfDoc.getDocumentInfo();
            pdfMetaData.setAuthor("Samuel Huylebroeck");
            pdfMetaData.addCreationDate();
            pdfMetaData.getProducer();
            pdfMetaData.setCreator("iText Software");
            pdfMetaData.setKeywords("example, accessibility");
            pdfMetaData.setSubject("PDF accessibility");
            //Title is derived from html
 
            // pdf conversion
            ConverterProperties props = new ConverterProperties();
            FontProvider fp = new FontProvider();
            fp.addStandardPdfFonts();
            fp.addDirectory(resources);//The noto-nashk font file (.ttf extension) is placed in the resources
 
            props.setFontProvider(fp);
            props.setBaseUri(resources);
            //Setup custom tagworker factory for better tagging of headers
            
            //DefaultTagWorkerFactory tagWorkerFactory = new AccessibilityTagWorkerFactory();
            //props.setTagWorkerFactory(tagWorkerFactory);
 
            HtmlConverter.convertToPdf(new FileInputStream(src), pdfDoc, props);
            pdfDoc.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
