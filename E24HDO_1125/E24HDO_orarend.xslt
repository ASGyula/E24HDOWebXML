<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <style>
                    body{
                    background-color: #f2f2f2;
                    font-family: Arial, sans-serif;
                    margin: 20px;
                    }
                    h2{
                    text-align: center;
                    color: #244062;
                    font-size: 24px;
                    margin-bottom: 15px;
                    }
                    table{
                    width: 95%;
                    margin: auto;
                    border-collapse: collapse;
                    border: 1px solid #8ea9db;
                    font-size: 13px;
                    }
                    th, td{
                    border: 1px solid #b4c6e7;
                    padding: 8px 12px;
                    text-align: center;
                    }
                    th, thead tr, table tr:first-child{
                    background-color: #244062;
                    color: white;
                    font-weight: bold;
                    }
                    tbody tr:nth-child(odd){
                    background-color: #d9e1f2;
                    }
                    tbody tr:nth-child(even){
                    background-color: #ffffff;
                    }
                </style>
            </head>
            <body style="background-color: darkgray;">
                <h2 style="text-align:center; color:darkblue;">E24HDO_órarend</h2>
                <table style="margin-left:auto; margin-right:auto;">
                    <tr>
                        <th>ID</th>
                        <th>Tantárgy</th>
                        <th>Típus</th>
                        <th>Nap</th>
                        <th>Kezdés</th>
                        <th>Befejezés</th>
                        <th>Helyszín</th>
                        <th>Oktató</th>
                        <th>Szak</th>
                    </tr>
                    <tbody>
                    <xsl:for-each select="E24HDO_orarend/ora">
                            <tr>
                                <td>
                                    <xsl:value-of select="@id"/>
                                </td>
                                <td>
                                    <xsl:value-of select="targy"/>
                                </td>
                                <td>
                                    <xsl:value-of select="@tipus"/>
                                </td>
                                <td>
                                    <xsl:value-of select="idopont/nap"/>
                                </td>
                                <td>
                                    <xsl:value-of select="idopont/tol"/>:00
                                </td>
                                <td>
                                    <xsl:value-of select="idopont/ig"/>:00
                                </td>
                                <td>
                                    <xsl:value-of select="helyszin"/>
                                </td>
                                <td>
                                    <xsl:value-of select="oktato"/>
                                </td>
                                <td>
                                    <xsl:value-of select="szak"/>
                                </td>
                            </tr>
                    </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>