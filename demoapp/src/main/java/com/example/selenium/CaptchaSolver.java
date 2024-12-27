package com.example.selenium;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
// import java.io.File;
// import java.io.IOException;
import java.util.Base64;

public class CaptchaSolver {
        
    private static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }
    
    private static BufferedImage extractDarkText(BufferedImage image) {
        // Membuat image baru untuk hasil ekstraksi
        BufferedImage extractedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
    
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                
                // Ambil intensitas abu-abu untuk mendeteksi bagian paling gelap (misalnya, teks)
                int gray = (color >> 16) & 0xff; // Ambil komponen warna abu-abu
    
                // Menggunakan threshold untuk mengidentifikasi warna paling gelap (teks)
                if (gray < 100) { // Threshold gelap, misalnya warna yang lebih gelap dari 100 (sesuaikan dengan kebutuhan)
                    extractedImage.setRGB(x, y, 0x000000); // Hitam (teks)
                } else {
                    extractedImage.setRGB(x, y, 0xFFFFFF); // Putih (latar belakang)
                }
            }
        }
        return extractedImage;
    }
    
    public static String decodeCaptcha(String base64Captcha) {
        try {
            // Decode Base64 menjadi BufferedImage
            byte[] imageBytes = Base64.getDecoder().decode(base64Captcha.split(",")[1]);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(byteArrayInputStream);
    
            // Preprocessing
            image = extractDarkText(image); // Menggunakan gambar yang sudah diproses
            image = resizeImage(image, image.getWidth() * 3, image.getHeight() * 3); // Perbesar gambar
            // saveImage(image, "processed_image.png");
   
            // OCR dengan Tesseract
            ITesseract tesseract = new Tesseract();
            tesseract.setLanguage("eng"); // Atur bahasa
            
            // Batasi pengenalan hanya ke huruf dan angka
            tesseract.setVariable("tessedit_char_whitelist", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    
            String ocrResult = tesseract.doOCR(image);
            return ocrResult.trim(); // Hapus spasi tambahan
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    // private static void saveImage(BufferedImage image, String filename) {
    //     try {
    //         ImageIO.write(image, "png", new File(filename));
    //         System.out.println("Gambar disimpan di " + filename);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public static void main(String[] args) {
        // Masukkan Base64 string gambar CAPTCHA di bawah ini
        String base64Captcha = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCABGAKADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDpIoovIsCdNyTjJ2x/vPkPv+PPpRLFF5F+RpuCM4O2P938g9/x49aIpYvIsAdSwRjI3R/u/kPt+HPrRLLF5F+BqWSc4G6P958g9vw49KAJmhh+2xD+ysDy3+XbHzyvPXt/Wq7/AGaO0BksVUm4wGYRjjzfu9fTj0/CvP8A4i+P7jS78aZol+zXCxkT3OEOwNg7VwOvAJPbtzmvONM0TWfFNzJLEWmO4eZcXE2BknuzHJ65OM+tAH0ckcDXcmNLDL5aEKFj45bnrjn+lRRRReRYE6bknGTtj/efIff8efSvE/8AhF/GPhZmuNOuwdgDMbG7DZ68beN3Tpg10Hg74lXt5qVjpGsl8mQRx3ESqpQ4KjcpHv17Dt3oA9JlNrt1CIWKCUAsF/d5jGwc4znrk8fzqy0MP22If2VgeW/y7Y+eV569v614D8R74XfjzUHinMqxFIlkyMnCjPTjrmpbC58d2FlBqNld3zW7oWjxOJgFzz8hJx0HBHagD3Pyovsuf7N5+0Y3bY+nm/d6/h6fhUyww/bZR/ZWR5afLtj45bnr3/pXj+gfFe/WeO11xt9s0qs08SKrId+4sRjn14x+PSvX47i3e6d11YFGiQhw8fzct7Y//XQBFFFF5FgTpuScZO2P958h9/x59KJYovIvyNNwRnB2x/u/kHv+PHrRFLF5FgDqWCMZG6P938h9vw59aJZYvIvwNSyTnA3R/vPkHt+HHpQBM0MP22If2VgeW/y7Y+eV569v61D5UX2XP9m8/aMbtsfTzfu9fw9PwqZpoftsR/tXI8t/m3R8crx07/0qHzYvsuP7S5+0Z27o+nm/e6fj6fhQBMsMP22Uf2VkeWny7Y+OW569/wClQxRReRYE6bknGTtj/efIff8AHn0qZZoftsp/tXA8tPm3R88tx07f1rNuNa0rTbew+2a7DARjKNLHuT5D2xn25z1oAuSxReRfkabgjODtj/d/IPf8ePWpmhh+2xD+ysDy3+XbHzyvPXt/WsWHxPoV9HfR2/iKB5HztjMiKZPkA4BAJ9OPStppoftsR/tXI8t/m3R8crx07/0oAh8qL7Ln+zeftGN22Pp5v3ev4en4VMsMP22Uf2VkeWny7Y+OW569/wClQ+bF9lx/aXP2jO3dH08373T8fT8KmWaH7bKf7VwPLT5t0fPLcdO39aACFrn7NpuIosDG396ef3bdfl44+tNupriOy1WR4otqqzPiU8YjHT5eePpUcXkeRYZ+1543Y83H3D0/+t29qbcpbS2epRn7XhgyjPm45jHX/wCv29qAPmi6uLjV9VluHy9xdzFj7sx/xNfSGiaY+ieGbTTYIYfLglVC/mEF2EvJPy9z/ntXzrcW0+ga+0FxGwms5xlSCpO05B9RkYP419FWN7YanosV5aS3TxTShkdTIRgyflux+OfegCWx8Q2d/q1zBa3dhLcIBE8QucMGVmBAG3J59vSqH/COWZ1jSNfFnBHeQpt8wSECQGI8uNvUDv8A5HjnjzwrbeFtRgFvfz3H2gGTE8RR156k9/rgd62/Cuo6hB4D1rUb2+vZIY1MFqjyOURvLYcdhyycfWgDgdUvW1LV7y+b71xO8p/4ExP9a9v0Px34cstI0+FNRtVWzs/KZWMikkBM8FOTx2zXimi2H9p6zaWZ3bZZAH29dvU/oDXo3iP4U2NraTXGkXt7uiheXyrmEndtxwCFGOp5x6Z60Aeb6lc/2prd5dQQlftVy8iRKMkbmJCj88V69p+lfE+GGK0g1DTbUQ20UYDhWIQZCg/IeeDmvPfAOo2Nj4ntEvbKOYTTIkc/zb4W3DBXB/DpnuDXsXivxRp3he1nuJDcPcyRKttA0kqmRstnOTnaMj+nJoA888Tav408NW9gt74ntnlkUPFb2yjeiYwGPyDA5x7/AIUat4o8UaJ4Qis765lXUNTmeWSV2/eRw7EAXGPlJzn1HTirvgfwvPrGpQ+JvEazyrJJmCNo3+fCnD8D7owMAenoOdL4o6Tpt7pv2x78Wt3bykQrc7/3ylFyoyCd2Rx+vGDQBwXh/wAG+IPF6SXltKmAWAluZiC7DGQDgn+IUaZ4p8QeENXa0nuJnjtp9txZyybkJVskDrg5HUfrVjwl4/m8MWTWj2Ju4ss0eLhoipbGemcj5enHX6VR0zSr7xv4lmnOEWafzLhwS3lqzdhyT1wKAPUPHXjLUbGW10zRkUalqsMXlPG24ohZgMZA+Yk49sflHofwx0+3gs7nVYV1K8uCGdpbhgvKE4wB+pJ6dqxL1LeP44WELicWsSxpEG37gBEcY/i+9Xp8XkeRYZ+1543Y83H3D0/+t29qAOZ1P4c6Be2l9t0mG1eLO2S3nZSnyA8DGD68iuugS8gktIXSGSSO3ZN3mkbsbAT93j/69V5fI8i/x9rzztz5uPuDr/8AX7e1TN9m+2xf8fmPLfP+uz1X8f8AI9qADdc/Y/8AVRY+09fNPXzv931/z2qdHuvt837mHd5SZHmnGMv/ALP1ql+4+y/8veftH/TXGPN/LOPxz71Mv2b7bL/x+Y8tMf67PVvx/wAn3oAIVufs2m4liwcbf3R4/dt1+bnj6UTLc/ZtSzLFgZ3fujz+7Xp83HH1qGKKLyLAnTck4ydsf7z5D7/jz6USxReRfkabgjODtj/d/IPf8ePWgDn/ABp4Aj8V3MUn2iK31ARNtmSIgOARhXG4569eo9681j0Px74OZ2sI7r7P5vJtf30bsrYB2c9x3Ar3BoYftsQ/srA8t/l2x88rz17f1qHyovsuf7N5+0Y3bY+nm/d6/h6fhQB89eIdQ8QeI9SS51W3na5jjEQAtymFBJ6Y9Sa9H1Dwvqw+EelaTp0Czz3DpNJEoCN8waQ5JbBxwMe1eiLDD9tlH9lZHlp8u2Pjluevf+lQxRReRYE6bknGTtj/AHnyH3/Hn0oA+e7ay8Q+FNSF82lXEUsIZN01uxQblIPPTo3r3rd1X4s65qunS2rW1nA0sTwvJErZ2tjOMscHjH417PLFF5F+RpuCM4O2P938g9/x49ar6jY6PHIbq80G3aOKCSSTfBExwNpJ69hn86APnjQVNtqEGqzIfsllMkrtjhmByEHqTjp6ZPQGu98NaJqPxK8Ry+INbZVsoSPLiZSUcAnCKMj5Rg5OeT+OOU1DXLHxF4ljkvwbLRYpCUt7eMblj3ZwAONxHft+FeiwfE3wVZYig025+zJEiRxpap8uM56t7j8qAO9gjuFtdMCSQquFCDyj8v7tv9rnj6V4J8TLq5uPHmopcvnySkaAAgAbF6DJ69a7uH4oaFMLOK30K+mkUgP+5j+c7SPU55INTeNvh8niGa51Kwja0vIsB1YL5bKEU84OQfcA8UAaHhvwXoT6FpgWy066FxZ+a00tvvZidmed2c5PbGOa8b1b/infGV2NKmKfYrtvJdT02t075HbnNb4+H/jyxP2S2hmCOCdsN6iqQMZ43D1FXfD/AMK9Va9hutahWOzSZQ8SSKzSHfgrwcAZ4z/+ugDR+I8OoWmraN4vhjUvFHC0uxCAjZLLu5PXkZ9gK9F0XUl1nSNKvLK7geJsADyyTGwjbKt83Ucjt61YmsbO6kuLafRlkgeFVaIpHjGW56/y9PpXncnwyurSSG58P6neWfngfu5NpUnaTwQwyODwR+NAHpFx56Wmps88IRQxcmMj/lmvfdxx9aytE8Uw+I9fvLbTrmGVbFNpnER2vuIzj5uR8vX6+xrjD8OdVvkuG1vW7yaKHO6KFVC52g85bHp0U13emaHpmiiCzsdGMcQjckMEZnOV+Yktyfr68UAXttz9j/1sWPtPTyj187/e9f8APep0S6+3zfvod3lJk+UcYy/+19apeVF9lz/ZvP2jG7bH0837vX8PT8KmWGH7bKP7KyPLT5dsfHLc9e/9KAIYpYvIsAdSwRjI3R/u/kPt+HPrRLLF5F+BqWSc4G6P958g9vw49Kmha5+zabiKLAxt/enn923X5eOPrRM1z9m1LMUWDnd+9PH7teny88fSgAaaH7bEf7VyPLf5t0fHK8dO/wDSofNi+y4/tLn7Rnbuj6eb97p+Pp+FXXe6+3w/uYd3lPgeacYyn+z9Kg3XP2P/AFUWPtPXzT187/d9f89qABZoftsp/tXA8tPm3R88tx07f1qGKWLyLAHUsEYyN0f7v5D7fhz61dR7r7fN+5h3eUmR5pxjL/7P1qCFrn7NpuIosDG396ef3bdfl44+tAEMssXkX4GpZJzgbo/3nyD2/Dj0p9x9luJxFNqKywvDIjhzEQQSuVI24IIzwfSnzNc/ZtSzFFg53fvTx+7Xp8vPH0qd3uvt8P7mHd5T4HmnGMp/s/SgDBj0TQIrbMcVgjefjCwQDjzOv3c9OfT8K0IbfTobyTy7uGNfLTDKIhnluPu4/wD11Puufsf+qix9p6+aevnf7vr/AJ7VOj3X2+b9zDu8pMjzTjGX/wBn60AUopYhb6eP7R24xld0f7v5D7fhz60SyxeRfgalknOBuj/efIPb8OPSpoWufs2m4iiwMbf3p5/dt1+Xjj60TNc/ZtSzFFg53fvTx+7Xp8vPH0oAGmh+2xH+1cjy3+bdHxyvHTv/AEqHzYvsuP7S5+0Z27o+nm/e6fj6fhV13uvt8P7mHd5T4HmnGMp/s/SoN1z9j/1UWPtPXzT187/d9f8APagAWaH7bKf7VwPLT5t0fPLcdO39ahili8iwB1LBGMjdH+7+Q+34c+tXUe6+3zfuYd3lJkeacYy/+z9agha5+zabiKLAxt/enn923X5eOPrQBDLLF5F+BqWSc4G6P958g9vw49KmaaH7bEf7VyPLf5t0fHK8dO/9KJmufs2pZiiwc7v3p4/dr0+Xnj6VO73X2+H9zDu8p8DzTjGU/wBn6UAUvNi+y4/tLn7Rnbuj6eb97p+Pp+FTLND9tlP9q4Hlp826PnluOnb+tG65+x/6qLH2nr5p6+d/u+v+e1To919vm/cw7vKTI804xl/9n60AQQ20ZttNO6X5sZ/fP/zzY8c8fhRNbRi21I7pflzj98//ADzU888/jRRQBO9pH9vhXdNgxOf9c+eqd81B9mj+x53S5+04/wBc/wDz2x6/r+NFFAE6Wkf2+Zd02BEh/wBc+er981BDbRm2007pfmxn98//ADzY8c8fhRRQATW0YttSO6X5c4/fP/zzU888/jU72kf2+Fd02DE5/wBc+eqd80UUAQfZo/sed0uftOP9c/8Az2x6/r+NTpaR/b5l3TYESH/XPnq/fNFFAEENtGbbTTul+bGf3z/882PHPH4UTW0YttSO6X5c4/fP/wA81PPPP40UUATvaR/b4V3TYMTn/XPnqnfNQfZo/sed0uftOP8AXP8A89sev6/jRRQBOlpH9vmXdNgRIf8AXPnq/fNQQ20ZttNO6X5sZ/fP/wA82PHPH4UUUAE1tGLbUjul+XOP3z/881PPPP41O9pH9vhXdNgxOf8AXPnqnfNFFAEH2aP7HndLn7Tj/XP/AM9sev6/jU6Wkf2+Zd02BEh/1z56v3zRRQB//9k=";

        // Panggil metode decodeCaptcha
        String result = CaptchaSolver.decodeCaptcha(base64Captcha);

        // Cetak hasil OCR
        if (result != null) {
            System.out.println("Teks dari CAPTCHA: " + result);
        } else {
            System.out.println("Gagal mendekode CAPTCHA.");
        }
    }
}