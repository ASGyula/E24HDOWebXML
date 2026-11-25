<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body>
                <h2>kocsikák</h2>
                <p>
                    Drágább autók száma, mint 30000:
                    <xsl:value-of select="count(autok/auto[ar &gt; 30000])" /> db
                </p>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>