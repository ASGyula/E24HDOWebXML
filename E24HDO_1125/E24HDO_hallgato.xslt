<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Hallgatók adatai - for-each, value-of</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>ID</th>
                        <th>Vezeteknev</th>
                        <th>Keresztnev</th>
                        <th>Becenev</th>
                        <th>Kor</th>
                        <th>Fizetes</th>
                    </tr>
                    <xsl:for-each select="class/hallgato">
                        <tr>
                            <td>
                                <xsl:value-of select="@id"/>
                            </td>
                            <td>
                                <xsl:value-of select="vezeteknev"/>
                            </td>
                            <td>
                                <xsl:value-of select="keresztnev"/>
                            </td>
                            <td>
                                <xsl:value-of select="becenev"/>
                            </td>
                            <td>
                                <xsl:value-of select="kor"/>
                            </td>
                            <td>
                                <xsl:value-of select="fizetes"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>