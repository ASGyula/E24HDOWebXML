<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body>
                <h2>kocsikák</h2>
                <ol>
                    <xsl:for-each select="autok/auto">
                        <xsl:sort select="ar" data-type="number" order="ascending"/>
                        <li>
                            <xsl:value-of select="@rsz"/>, ár <xsl:value-of select="ar"/>
                        </li>
                    </xsl:for-each>
                </ol>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>